package uk.rinzler.tv.ui.settings.screen.authentication

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uk.rinzler.tv.R
import uk.rinzler.tv.auth.repository.AuthenticationRepository
import uk.rinzler.tv.auth.repository.ServerRepository
import uk.rinzler.tv.auth.repository.ServerUserRepository
import uk.rinzler.tv.auth.store.AuthenticationPreferences
import uk.rinzler.tv.preference.constant.UserSelectBehavior
import uk.rinzler.tv.ui.base.ProfilePicture
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.button.IconButtonDefaults
import uk.rinzler.tv.ui.base.form.RadioButton
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsAuthenticationAutoSignInScreen() {
	val router = LocalRouter.current
	val serverRepository = koinInject<ServerRepository>()
	val serverUserRepository = koinInject<ServerUserRepository>()
	val authenticationRepository = koinInject<AuthenticationRepository>()
	val authenticationPreferences = koinInject<AuthenticationPreferences>()
	var autoLoginUserBehavior by rememberPreference(authenticationPreferences, AuthenticationPreferences.autoLoginUserBehavior)
	var autoLoginServerId by rememberPreference(authenticationPreferences, AuthenticationPreferences.autoLoginServerId)
	var autoLoginUserId by rememberPreference(authenticationPreferences, AuthenticationPreferences.autoLoginUserId)

	LaunchedEffect(serverRepository) {
		serverRepository.loadStoredServers()
	}

	val storedServers by serverRepository.storedServers.collectAsState()

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_login).uppercase()) },
				headingContent = { Text(stringResource(R.string.auto_sign_in)) },
			)
		}

		item {
			ListButton(
				headingContent = { Text(stringResource(R.string.user_picker_disable_title)) },
				captionContent = { Text(stringResource(R.string.user_picker_disable_summary)) },
				trailingContent = { RadioButton(checked = autoLoginUserBehavior == UserSelectBehavior.DISABLED) },
				onClick = {
					autoLoginUserBehavior = UserSelectBehavior.DISABLED
					router.back()
				}
			)
		}

		item {
			ListButton(
				headingContent = { Text(stringResource(R.string.user_picker_last_user_title)) },
				captionContent = { Text(stringResource(R.string.user_picker_last_user_summary)) },
				trailingContent = { RadioButton(checked = autoLoginUserBehavior == UserSelectBehavior.LAST_USER) },
				onClick = {
					autoLoginUserBehavior = UserSelectBehavior.LAST_USER
					router.back()
				}
			)
		}

		for (server in storedServers) {
			item { ListSection(headingContent = { Text(server.name) }) }

			val users = serverUserRepository.getStoredServerUsers(server)
			val serverId = server.id.toString()
			items(users) { user ->
				val userId = user.id.toString()

				ListButton(
					leadingContent = {
						ProfilePicture(
							url = authenticationRepository.getUserImageUrl(server, user),
							modifier = Modifier
								.size(24.dp)
								.clip(IconButtonDefaults.Shape)
						)
					},
					headingContent = { Text(user.name) },
					trailingContent = { RadioButton(checked = autoLoginUserBehavior == UserSelectBehavior.SPECIFIC_USER && autoLoginServerId == serverId && autoLoginUserId == userId) },
					onClick = {
						autoLoginUserBehavior = UserSelectBehavior.SPECIFIC_USER
						autoLoginServerId = serverId
						autoLoginUserId = userId

						router.back()
					}
				)
			}
		}
	}
}
