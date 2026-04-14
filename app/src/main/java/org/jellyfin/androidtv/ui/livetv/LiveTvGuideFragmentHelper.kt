package uk.rinzler.tv.ui.livetv

import android.content.Context
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uk.rinzler.tv.R
import uk.rinzler.tv.data.model.DataRefreshService
import uk.rinzler.tv.data.repository.ItemMutationRepository
import uk.rinzler.tv.databinding.LiveTvGuideBinding
import uk.rinzler.tv.ui.GuideChannelHeader
import uk.rinzler.tv.ui.base.JellyfinTheme
import uk.rinzler.tv.ui.navigation.ProvideRouter
import uk.rinzler.tv.ui.settings.Routes
import uk.rinzler.tv.ui.settings.composable.SettingsDialog
import uk.rinzler.tv.ui.settings.composable.SettingsRouterContent
import uk.rinzler.tv.ui.settings.routes
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.userLibraryApi
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemKind
import org.jellyfin.sdk.model.api.MediaType
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

fun createNoProgramDataBaseItem(
	context: Context,
	channelId: UUID?,
	startDate: LocalDateTime?,
	endDate: LocalDateTime?
) = BaseItemDto(
	id = UUID.randomUUID(),
	type = BaseItemKind.FOLDER,
	mediaType = MediaType.UNKNOWN,
	name = context.getString(R.string.no_program_data),
	channelId = channelId,
	startDate = startDate,
	endDate = endDate,
)

fun LiveTvGuideFragment.toggleFavorite() {
	val header = mSelectedProgramView as? GuideChannelHeader
	val channel = header?.channel ?: return

	val itemMutationRepository by inject<ItemMutationRepository>()
	val dataRefreshService by inject<DataRefreshService>()

	lifecycleScope.launch {
		runCatching {
			val userData = itemMutationRepository.setFavorite(
				item = header.channel.id,
				favorite = !(channel.userData?.isFavorite ?: false)
			)

			header.channel = header.channel.copy(userData = userData)
			header.findViewById<View>(R.id.favImage).isVisible = userData.isFavorite
			dataRefreshService.lastFavoriteUpdate = Instant.now()
		}
	}
}

fun LiveTvGuideFragment.refreshSelectedProgram() {
	val api by inject<ApiClient>()

	lifecycleScope.launch {
		runCatching {
			val item = withContext(Dispatchers.IO) {
				api.userLibraryApi.getItem(mSelectedProgram.id).content
			}
			mSelectedProgram = item
		}.onFailure { error ->
			Timber.e(error, "Unable to get program details")
		}

		detailUpdateInternal();
	}
}

fun LiveTvGuideFragment.addSettingsOptions(binding: LiveTvGuideBinding): MutableStateFlow<Boolean> {
	val visible = MutableStateFlow(false)

	binding.settingsOptions.setContent {
		val isVisible by visible.collectAsState(false)

		JellyfinTheme {
			ProvideRouter(
				routes,
				Routes.LIVETV_GUIDE_OPTIONS
			) {
				SettingsDialog(
					visible = isVisible,
					onDismissRequest = {
						visible.value = false
						TvManager.forceReload()
						doLoad()
					}
				) {
					SettingsRouterContent()
				}
			}
		}
	}

	return visible
}

fun LiveTvGuideFragment.addSettingsFilters(binding: LiveTvGuideBinding): MutableStateFlow<Boolean> {
	val visible = MutableStateFlow(false)

	binding.settingsFilters.setContent {
		val isVisible by visible.collectAsState(false)

		JellyfinTheme {
			ProvideRouter(
				routes,
				Routes.LIVETV_GUIDE_FILTERS
			) {
				SettingsDialog(
					visible = isVisible,
					onDismissRequest = {
						visible.value = false
						TvManager.forceReload()
						doLoad()
					}
				) {
					SettingsRouterContent()
				}
			}
		}
	}

	return visible
}
