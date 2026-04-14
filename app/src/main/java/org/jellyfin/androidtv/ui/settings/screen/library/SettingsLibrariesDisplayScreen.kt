package uk.rinzler.tv.ui.settings.screen.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uk.rinzler.tv.R
import uk.rinzler.tv.auth.repository.ServerRepository
import uk.rinzler.tv.auth.repository.UserRepository
import uk.rinzler.tv.auth.store.AuthenticationStore
import uk.rinzler.tv.di.defaultDeviceInfo
import uk.rinzler.tv.preference.LibraryPreferences
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.Checkbox
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.Routes
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import uk.rinzler.tv.util.sdk.forUser
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.userApi
import org.jellyfin.sdk.model.DeviceInfo
import org.jellyfin.sdk.model.api.UserConfiguration
import org.jellyfin.sdk.model.api.CollectionType
import org.koin.compose.koinInject
import java.util.UUID

@Composable
fun SettingsLibrariesDisplayScreen(
	itemId: UUID,
	displayPreferencesId: String,
	serverId: UUID,
	userId: UUID
) {
	val router = LocalRouter.current
	val userView = rememberUserView(itemId)
	val prefs = rememberLibraryPreferences(displayPreferencesId, serverId, userId) ?: return

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_libraries).uppercase()) },
				headingContent = { Text(userView?.name.orEmpty()) },
			)
		}

		item {
			var posterSize by rememberPreference(prefs, LibraryPreferences.posterSize)

			ListButton(
				headingContent = { Text(stringResource(R.string.lbl_image_size)) },
				captionContent = { Text(stringResource(posterSize.nameRes)) },
				onClick = {
					router.push(
						Routes.LIBRARIES_DISPLAY_IMAGE_SIZE,
						mapOf(
							"itemId" to itemId.toString(),
							"displayPreferencesId" to displayPreferencesId,
							"serverId" to serverId.toString(),
							"userId" to userId.toString()
						)
					)
				}
			)
		}

		val showImageType = userView?.collectionType in setOf(
			CollectionType.MOVIES, CollectionType.TVSHOWS,
			CollectionType.LIVETV,
		)

		if (showImageType) {
			item {
				var imageType by rememberPreference(prefs, LibraryPreferences.imageType)

				ListButton(
					headingContent = { Text(stringResource(R.string.lbl_image_type)) },
					captionContent = { Text(stringResource(imageType.nameRes)) },
					onClick = {
						router.push(
							Routes.LIBRARIES_DISPLAY_IMAGE_TYPE,
							mapOf(
								"itemId" to itemId.toString(),
								"displayPreferencesId" to displayPreferencesId,
								"serverId" to serverId.toString(),
								"userId" to userId.toString()
							)
						)
					}
				)
			}
		}

		item {
			var gridDirection by rememberPreference(prefs, LibraryPreferences.gridDirection)

			ListButton(
				headingContent = { Text(stringResource(R.string.grid_direction)) },
				captionContent = { Text(stringResource(gridDirection.nameRes)) },
				onClick = {
					router.push(
						Routes.LIBRARIES_DISPLAY_GRID,
						mapOf(
							"itemId" to itemId.toString(),
							"displayPreferencesId" to displayPreferencesId,
							"serverId" to serverId.toString(),
							"userId" to userId.toString()
						)
					)
				}
			)
		}

		item {
			val userRepository = koinInject<UserRepository>()
			val serverRepository = koinInject<ServerRepository>()
			val authenticationStore = koinInject<AuthenticationStore>()
			val jellyfin = koinInject<Jellyfin>()
			val deviceInfo = koinInject<DeviceInfo>(defaultDeviceInfo)
			val currentApi = koinInject<ApiClient>()
			val scope = rememberCoroutineScope()

			// Resolve the correct API client and user config for the library's server
			var serverApi by remember { mutableStateOf<ApiClient?>(null) }
			var userConfig by remember { mutableStateOf<UserConfiguration?>(null) }
			var hidden by remember { mutableStateOf(false) }

			LaunchedEffect(serverId, userId) {
				val server = serverRepository.getServer(serverId)
				val serverStore = authenticationStore.getServer(serverId)
				val userInfo = serverStore?.users?.get(userId)

				val api = if (server != null && userInfo != null && !userInfo.accessToken.isNullOrBlank()) {
					val userDeviceInfo = deviceInfo.forUser(userId)
					jellyfin.createApi(
						baseUrl = server.address,
						accessToken = userInfo.accessToken,
						deviceInfo = userDeviceInfo
					)
				} else {
					currentApi
				}

				serverApi = api
				val user = withContext(Dispatchers.IO) { api.userApi.getCurrentUser() }.content
				userConfig = user.configuration
				hidden = itemId in (user.configuration?.myMediaExcludes.orEmpty())
			}

			ListButton(
				headingContent = { Text(stringResource(R.string.lbl_hide_from_navbar)) },
				trailingContent = { Checkbox(checked = hidden) },
				captionContent = { Text(stringResource(R.string.lbl_hide_from_navbar_description)) },
				onClick = {
					val api = serverApi ?: return@ListButton
					val config = userConfig ?: return@ListButton
					hidden = !hidden
					scope.launch(Dispatchers.IO) {
						val updatedExcludes = if (hidden) {
							config.myMediaExcludes + itemId
						} else {
							config.myMediaExcludes - itemId
						}
						val updatedConfig = config.copy(myMediaExcludes = updatedExcludes)
						api.userApi.updateUserConfiguration(data = updatedConfig)
						userConfig = updatedConfig

						// Update the cached current user if this is the active session
						val currentUser = userRepository.currentUser.value
						if (currentUser?.id == userId) {
							userRepository.setCurrentUser(currentUser.copy(configuration = updatedConfig))
						}
					}
				}
			)
		}
	}
}
