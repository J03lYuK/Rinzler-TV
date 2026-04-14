package uk.rinzler.tv.ui.settings

import uk.rinzler.tv.R
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.ui.navigation.RouteComposable
import uk.rinzler.tv.ui.settings.composable.SettingsNumericScreen
import uk.rinzler.tv.ui.settings.screen.SettingsDeveloperScreen
import uk.rinzler.tv.ui.settings.screen.SettingsMainScreen
import uk.rinzler.tv.ui.settings.screen.SettingsTelemetryScreen
import uk.rinzler.tv.ui.settings.screen.about.SettingsAboutScreen
import uk.rinzler.tv.ui.settings.screen.authentication.SettingsAuthenticationAutoSignInScreen
import uk.rinzler.tv.ui.settings.screen.authentication.SettingsAuthenticationPinCodeScreen
import uk.rinzler.tv.ui.settings.screen.authentication.SettingsAuthenticationScreen
import uk.rinzler.tv.ui.settings.screen.authentication.SettingsAuthenticationServerScreen
import uk.rinzler.tv.ui.settings.screen.authentication.SettingsAuthenticationServerUserScreen
import uk.rinzler.tv.ui.settings.screen.authentication.SettingsAuthenticationSortByScreen
import uk.rinzler.tv.ui.settings.screen.customization.SettingsCustomizationClockScreen
import uk.rinzler.tv.ui.settings.screen.customization.SettingsCustomizationScreen
import uk.rinzler.tv.ui.settings.screen.customization.SettingsCustomizationThemeScreen
import uk.rinzler.tv.ui.settings.screen.customization.SettingsCustomizationWatchedIndicatorScreen
import uk.rinzler.tv.ui.settings.screen.customization.subtitle.SettingsSubtitleTextStrokeColorScreen
import uk.rinzler.tv.ui.settings.screen.customization.subtitle.SettingsSubtitlesBackgroundColorScreen
import uk.rinzler.tv.ui.settings.screen.customization.subtitle.SettingsSubtitlesScreen
import uk.rinzler.tv.ui.settings.screen.customization.subtitle.SettingsSubtitlesTextColorScreen
import uk.rinzler.tv.ui.settings.screen.home.SettingsHomePosterSizeScreen
import uk.rinzler.tv.ui.settings.screen.home.SettingsHomeScreen
import uk.rinzler.tv.ui.settings.screen.home.SettingsHomeSectionScreen
import uk.rinzler.tv.ui.settings.screen.library.SettingsLibrariesDisplayGridScreen
import uk.rinzler.tv.ui.settings.screen.library.SettingsLibrariesDisplayImageSizeScreen
import uk.rinzler.tv.ui.settings.screen.library.SettingsLibrariesDisplayImageTypeScreen
import uk.rinzler.tv.ui.settings.screen.library.SettingsLibrariesDisplayScreen
import uk.rinzler.tv.ui.settings.screen.library.SettingsLibrariesScreen
import uk.rinzler.tv.ui.settings.screen.license.SettingsLicenseScreen
import uk.rinzler.tv.ui.settings.screen.license.SettingsLicensesScreen
import uk.rinzler.tv.ui.settings.screen.livetv.SettingsLiveTvGuideChannelOrderScreen
import uk.rinzler.tv.ui.settings.screen.livetv.SettingsLiveTvGuideFiltersScreen
import uk.rinzler.tv.ui.settings.screen.livetv.SettingsLiveTvGuideOptionsScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsJellyseerrRowsScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsJellyseerrScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerBrowsingBlurScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerDetailsBlurScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerHomeRowsImageScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerMediaBarColorScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerMediaBarContentTypeScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerMediaBarExcludedGenresScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerMediaBarItemCountScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerMediaBarOpacityScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerMediaBarSourceTypeScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerNavbarPositionScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerParentalControlsScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsPluginScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsPluginAppearanceScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsPluginHomeScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsPluginMediaBarScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsPluginRatingsScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsPluginThemeMusicScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsPluginToolbarScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerSeasonalSurpriseScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerShuffleContentTypeScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerSyncPlayScreen
import uk.rinzler.tv.ui.settings.screen.moonfin.SettingsRinzlerThemeMusicVolumeScreen
import uk.rinzler.tv.ui.settings.screen.playback.SettingsPlaybackAdvancedScreen
import uk.rinzler.tv.ui.settings.screen.playback.SettingsPlaybackAudioBehaviorScreen
import uk.rinzler.tv.ui.settings.screen.playback.SettingsPlaybackInactivityPromptScreen
import uk.rinzler.tv.ui.settings.screen.playback.SettingsPlaybackMaxBitrateScreen
import uk.rinzler.tv.ui.settings.screen.playback.SettingsPlaybackMaxResolutionScreen
import uk.rinzler.tv.ui.settings.screen.playback.SettingsPlaybackPlayerScreen
import uk.rinzler.tv.ui.settings.screen.playback.SettingsPlaybackPrerollsScreen
import uk.rinzler.tv.ui.settings.screen.playback.SettingsPlaybackRefreshRateSwitchingBehaviorScreen
import uk.rinzler.tv.ui.settings.screen.playback.SettingsPlaybackResumeSubtractDurationScreen
import uk.rinzler.tv.ui.settings.screen.playback.SettingsPlaybackScreen
import uk.rinzler.tv.ui.settings.screen.playback.SettingsPlaybackZoomModeScreen
import uk.rinzler.tv.ui.settings.screen.playback.mediasegment.SettingsPlaybackMediaSegmentScreen
import uk.rinzler.tv.ui.settings.screen.playback.mediasegment.SettingsPlaybackMediaSegmentsScreen
import uk.rinzler.tv.ui.settings.screen.playback.nextup.SettingsPlaybackNextUpBehaviorScreen
import uk.rinzler.tv.ui.settings.screen.playback.nextup.SettingsPlaybackNextUpScreen
import uk.rinzler.tv.ui.settings.screen.screensaver.SettingsScreensaverAgeRatingScreen
import uk.rinzler.tv.ui.settings.screen.screensaver.SettingsScreensaverDimmingScreen
import uk.rinzler.tv.ui.settings.screen.screensaver.SettingsScreensaverModeScreen
import uk.rinzler.tv.ui.settings.screen.screensaver.SettingsScreensaverScreen
import uk.rinzler.tv.ui.settings.screen.screensaver.SettingsScreensaverTimeoutScreen
import org.jellyfin.sdk.model.api.MediaSegmentType
import org.jellyfin.sdk.model.serializer.toUUIDOrNull

object Routes {
	const val MAIN = "/"
	const val AUTHENTICATION = "/authentication"
	const val AUTHENTICATION_FROM_LOGIN = "/authentication+login"
	const val AUTHENTICATION_SERVER = "/authentication/server/{serverId}"
	const val AUTHENTICATION_SERVER_USER = "/authentication/server/{serverId}/user/{userId}"
	const val AUTHENTICATION_SORT_BY = "/authentication/sort-by"
	const val AUTHENTICATION_AUTO_SIGN_IN = "/authentication/auto-sign-in"
	const val AUTHENTICATION_PIN_CODE = "/authentication/pin-code"
	const val CUSTOMIZATION = "/customization"
	const val CUSTOMIZATION_THEME = "/customization/theme"
	const val CUSTOMIZATION_CLOCK = "/customization/clock"
	const val CUSTOMIZATION_WATCHED_INDICATOR = "/customization/watch-indicators"
	const val CUSTOMIZATION_SCREENSAVER = "/customization/screensaver"
	const val CUSTOMIZATION_SCREENSAVER_TIMEOUT = "/customization/screensaver/timeout"
	const val CUSTOMIZATION_SCREENSAVER_AGE_RATING = "/customization/screensaver/age-rating"
	const val CUSTOMIZATION_SCREENSAVER_MODE = "/customization/screensaver/mode"
	const val CUSTOMIZATION_SCREENSAVER_DIMMING = "/customization/screensaver/dimming"
	const val CUSTOMIZATION_SUBTITLES = "/customization/subtitles"
	const val CUSTOMIZATION_SUBTITLES_TEXT_COLOR = "/customization/subtitles/text-color"
	const val CUSTOMIZATION_SUBTITLES_BACKGROUND_COLOR = "/customization/subtitles/background-color"
	const val CUSTOMIZATION_SUBTITLES_EDGE_COLOR = "/customization/subtitles/edge-color"
	const val LIBRARIES = "/libraries"
	const val LIBRARIES_DISPLAY = "/libraries/display/{itemId}/{displayPreferencesId}/{serverId}/{userId}"
	const val LIBRARIES_DISPLAY_IMAGE_SIZE = "/libraries/display/{itemId}/{displayPreferencesId}/{serverId}/{userId}/image-size"
	const val LIBRARIES_DISPLAY_IMAGE_TYPE = "/libraries/display/{itemId}/{displayPreferencesId}/{serverId}/{userId}/image-type"
	const val LIBRARIES_DISPLAY_GRID = "/libraries/display/{itemId}/{displayPreferencesId}/{serverId}/{userId}/grid"
	const val HOME = "/home"
	const val HOME_SECTION = "/home/section/{index}"
	const val HOME_POSTER_SIZE = "/home/poster-size"
	const val HOME_ROWS_IMAGE_TYPE = "/home/rows-image-type"
	const val LIVETV_GUIDE_FILTERS = "/livetv/guide/filters"
	const val LIVETV_GUIDE_OPTIONS = "/livetv/guide/options"
	const val LIVETV_GUIDE_CHANNEL_ORDER = "/livetv/guide/channel-order"
	const val PLAYBACK = "/playback"
	const val PLAYBACK_PLAYER = "/playback/player"
	const val PLAYBACK_NEXT_UP = "/playback/next-up"
	const val PLAYBACK_NEXT_UP_BEHAVIOR = "/playback/next-up/behavior"
	const val PLAYBACK_INACTIVITY_PROMPT = "/playback/inactivity-prompt"
	const val PLAYBACK_PREROLLS = "/playback/prerolls"
	const val PLAYBACK_MEDIA_SEGMENTS = "/playback/media-segments"
	const val PLAYBACK_MEDIA_SEGMENT = "/playback/media-segments/{segmentType}"
	const val PLAYBACK_ADVANCED = "/playback/advanced"
	const val PLAYBACK_RESUME_SUBTRACT_DURATION = "/playback/resume-subtract-duration"
	const val PLAYBACK_MAX_BITRATE = "/playback/max-bitrate"
	const val PLAYBACK_MAX_RESOLUTION = "/playback/max-resolution"
	const val PLAYBACK_REFRESH_RATE_SWITCHING_BEHAVIOR = "/playback/refresh-rate-switching-behavior"
	const val PLAYBACK_ZOOM_MODE = "/playback/zoom-mode"
	const val PLAYBACK_AUDIO_BEHAVIOR = "/playback/audio-behavior"
	const val JELLYSEERR = "/jellyseerr"
	const val JELLYSEERR_ROWS = "/jellyseerr/rows"
	const val PLUGIN = "/plugin"
	const val PLUGIN_TOOLBAR = "/plugin/toolbar"
	const val PLUGIN_HOME = "/plugin/home"
	const val PLUGIN_MEDIA_BAR = "/plugin/media-bar"
	const val PLUGIN_THEME_MUSIC = "/plugin/theme-music"
	const val PLUGIN_APPEARANCE = "/plugin/appearance"
	const val PLUGIN_RATINGS = "/plugin/ratings"
	const val MOONFIN_NAVBAR_POSITION = "/moonfin/navbar-position"
	const val MOONFIN_SHUFFLE_CONTENT_TYPE = "/moonfin/shuffle-content-type"
	const val MOONFIN_MEDIA_BAR_SOURCE_TYPE = "/moonfin/media-bar-source-type"
	const val MOONFIN_MEDIA_BAR_EXCLUDED_GENRES = "/moonfin/media-bar-excluded-genres"
	const val MOONFIN_MEDIA_BAR_CONTENT_TYPE = "/moonfin/media-bar-content-type"
	const val MOONFIN_MEDIA_BAR_ITEM_COUNT = "/moonfin/media-bar-item-count"
	const val MOONFIN_MEDIA_BAR_OPACITY = "/moonfin/media-bar-opacity"
	const val MOONFIN_MEDIA_BAR_COLOR = "/moonfin/media-bar-color"
	const val MOONFIN_THEME_MUSIC_VOLUME = "/moonfin/theme-music-volume"
	const val MOONFIN_SEASONAL_SURPRISE = "/moonfin/seasonal-surprise"
	@Deprecated("Moved to HOME_ROWS_IMAGE_TYPE", replaceWith = ReplaceWith("HOME_ROWS_IMAGE_TYPE"))
	const val MOONFIN_HOME_ROWS_IMAGE = "/moonfin/home-rows-image"
	const val MOONFIN_DETAILS_BLUR = "/moonfin/details-blur"
	const val MOONFIN_BROWSING_BLUR = "/moonfin/browsing-blur"
	const val MOONFIN_PARENTAL_CONTROLS = "/moonfin/parental-controls"
	const val MOONFIN_SYNCPLAY = "/moonfin/syncplay"
	const val MOONFIN_SYNCPLAY_MIN_DELAY = "/moonfin/syncplay/min-delay-speed-to-sync"
	const val MOONFIN_SYNCPLAY_MAX_DELAY = "/moonfin/syncplay/max-delay-speed-to-sync"
	const val MOONFIN_SYNCPLAY_DURATION = "/moonfin/syncplay/speed-to-sync-duration"
	const val MOONFIN_SYNCPLAY_MIN_DELAY_SKIP = "/moonfin/syncplay/min-delay-skip-to-sync"
	const val MOONFIN_SYNCPLAY_EXTRA_OFFSET = "/moonfin/syncplay/extra-time-offset"
	const val SYNCPLAY = "/syncplay"
	const val TELEMETRY = "/telemetry"
	const val DEVELOPER = "/developer"
	const val ABOUT = "/about"
	const val LICENSES = "/licenses"
	const val LICENSE = "/license/{artifactId}"
}

val routes = mapOf<String, RouteComposable>(
	Routes.MAIN to {
		SettingsMainScreen()
	},
	Routes.AUTHENTICATION to {
		SettingsAuthenticationScreen(false)
	},
	Routes.AUTHENTICATION_FROM_LOGIN to {
		SettingsAuthenticationScreen(true)
	},
	Routes.AUTHENTICATION_SERVER to { context ->
		val serverId = context.parameters["serverId"]?.toUUIDOrNull()
		if (serverId != null) {
			SettingsAuthenticationServerScreen(serverId = serverId)
		}
	},
	Routes.AUTHENTICATION_SERVER_USER to { context ->
		val serverId = context.parameters["serverId"]?.toUUIDOrNull()
		val userId = context.parameters["userId"]?.toUUIDOrNull()
		if (serverId != null && userId != null) {
			SettingsAuthenticationServerUserScreen(
				serverId = serverId,
				userId = userId
			)
		}
	},
	Routes.AUTHENTICATION_SORT_BY to {
		SettingsAuthenticationSortByScreen()
	},
	Routes.AUTHENTICATION_AUTO_SIGN_IN to {
		SettingsAuthenticationAutoSignInScreen()
	},
	Routes.AUTHENTICATION_PIN_CODE to {
		SettingsAuthenticationPinCodeScreen()
	},
	Routes.CUSTOMIZATION to {
		SettingsCustomizationScreen()
	},
	Routes.CUSTOMIZATION_THEME to {
		SettingsCustomizationThemeScreen()
	},
	Routes.CUSTOMIZATION_CLOCK to {
		SettingsCustomizationClockScreen()
	},
	Routes.CUSTOMIZATION_WATCHED_INDICATOR to {
		SettingsCustomizationWatchedIndicatorScreen()
	},
	Routes.CUSTOMIZATION_SCREENSAVER to {
		SettingsScreensaverScreen()
	},
	Routes.CUSTOMIZATION_SCREENSAVER_TIMEOUT to {
		SettingsScreensaverTimeoutScreen()
	},
	Routes.CUSTOMIZATION_SCREENSAVER_AGE_RATING to {
		SettingsScreensaverAgeRatingScreen()
	},
	Routes.CUSTOMIZATION_SCREENSAVER_MODE to {
		SettingsScreensaverModeScreen()
	},
	Routes.CUSTOMIZATION_SCREENSAVER_DIMMING to {
		SettingsScreensaverDimmingScreen()
	},
	Routes.CUSTOMIZATION_SUBTITLES to {
		SettingsSubtitlesScreen()
	},
	Routes.CUSTOMIZATION_SUBTITLES_TEXT_COLOR to {
		SettingsSubtitlesTextColorScreen()
	},
	Routes.CUSTOMIZATION_SUBTITLES_BACKGROUND_COLOR to {
		SettingsSubtitlesBackgroundColorScreen()
	},
	Routes.CUSTOMIZATION_SUBTITLES_EDGE_COLOR to {
		SettingsSubtitleTextStrokeColorScreen()
	},
	Routes.LIBRARIES to {
		SettingsLibrariesScreen()
	},
	Routes.LIBRARIES_DISPLAY to { context ->
		val itemId = context.parameters["itemId"]?.toUUIDOrNull()
		val displayPreferencesId = context.parameters["displayPreferencesId"]
		val serverId = context.parameters["serverId"]?.toUUIDOrNull()
		val userId = context.parameters["userId"]?.toUUIDOrNull()
		
		if (itemId != null && displayPreferencesId != null && serverId != null && userId != null) {
			SettingsLibrariesDisplayScreen(
				itemId = itemId,
				displayPreferencesId = displayPreferencesId,
				serverId = serverId,
				userId = userId
			)
		}
	},
	Routes.LIBRARIES_DISPLAY_IMAGE_SIZE to { context ->
		val itemId = context.parameters["itemId"]?.toUUIDOrNull()
		val displayPreferencesId = context.parameters["displayPreferencesId"]
		val serverId = context.parameters["serverId"]?.toUUIDOrNull()
		val userId = context.parameters["userId"]?.toUUIDOrNull()
		
		if (itemId != null && displayPreferencesId != null && serverId != null && userId != null) {
			SettingsLibrariesDisplayImageSizeScreen(
				itemId = itemId,
				displayPreferencesId = displayPreferencesId,
				serverId = serverId,
				userId = userId
			)
		}
	},
	Routes.LIBRARIES_DISPLAY_IMAGE_TYPE to { context ->
		val itemId = context.parameters["itemId"]?.toUUIDOrNull()
		val displayPreferencesId = context.parameters["displayPreferencesId"]
		val serverId = context.parameters["serverId"]?.toUUIDOrNull()
		val userId = context.parameters["userId"]?.toUUIDOrNull()
		
		if (itemId != null && displayPreferencesId != null && serverId != null && userId != null) {
			SettingsLibrariesDisplayImageTypeScreen(
				itemId = itemId,
				displayPreferencesId = displayPreferencesId,
				serverId = serverId,
				userId = userId
			)
		}
	},
	Routes.LIBRARIES_DISPLAY_GRID to { context ->
		val itemId = context.parameters["itemId"]?.toUUIDOrNull()
		val displayPreferencesId = context.parameters["displayPreferencesId"]
		val serverId = context.parameters["serverId"]?.toUUIDOrNull()
		val userId = context.parameters["userId"]?.toUUIDOrNull()
		
		if (itemId != null && displayPreferencesId != null && serverId != null && userId != null) {
			SettingsLibrariesDisplayGridScreen(
				itemId = itemId,
				displayPreferencesId = displayPreferencesId,
				serverId = serverId,
				userId = userId
			)
		}
	},
	Routes.HOME to {
		SettingsHomeScreen()
	},
	Routes.HOME_SECTION to { context ->
		val index = context.parameters["index"]?.toIntOrNull()
		if (index != null) {
			SettingsHomeSectionScreen(index)
		}
	},
	Routes.HOME_POSTER_SIZE to {
		SettingsHomePosterSizeScreen()
	},
	Routes.HOME_ROWS_IMAGE_TYPE to {
		SettingsRinzlerHomeRowsImageScreen()
	},
	Routes.LIVETV_GUIDE_FILTERS to {
		SettingsLiveTvGuideFiltersScreen()
	},
	Routes.LIVETV_GUIDE_OPTIONS to {
		SettingsLiveTvGuideOptionsScreen()
	},
	Routes.LIVETV_GUIDE_CHANNEL_ORDER to {
		SettingsLiveTvGuideChannelOrderScreen()
	},
	Routes.PLAYBACK to {
		SettingsPlaybackScreen()
	},
	Routes.PLAYBACK_PLAYER to {
		SettingsPlaybackPlayerScreen()
	},
	Routes.PLAYBACK_NEXT_UP to {
		SettingsPlaybackNextUpScreen()
	},
	Routes.PLAYBACK_NEXT_UP_BEHAVIOR to {
		SettingsPlaybackNextUpBehaviorScreen()
	},
	Routes.PLAYBACK_INACTIVITY_PROMPT to {
		SettingsPlaybackInactivityPromptScreen()
	},
	Routes.PLAYBACK_PREROLLS to {
		SettingsPlaybackPrerollsScreen()
	},
	Routes.PLAYBACK_MEDIA_SEGMENTS to {
		SettingsPlaybackMediaSegmentsScreen()
	},
	Routes.PLAYBACK_MEDIA_SEGMENT to { context ->
		val segmentType = context.parameters["segmentType"]?.let(MediaSegmentType::fromNameOrNull)
		if (segmentType != null) {
			SettingsPlaybackMediaSegmentScreen(segmentType = segmentType)
		}
	},
	Routes.PLAYBACK_ADVANCED to {
		SettingsPlaybackAdvancedScreen()
	},
	Routes.PLAYBACK_RESUME_SUBTRACT_DURATION to {
		SettingsPlaybackResumeSubtractDurationScreen()
	},
	Routes.PLAYBACK_MAX_BITRATE to {
		SettingsPlaybackMaxBitrateScreen()
	},
	Routes.PLAYBACK_MAX_RESOLUTION to {
		SettingsPlaybackMaxResolutionScreen()
	},
	Routes.PLAYBACK_REFRESH_RATE_SWITCHING_BEHAVIOR to {
		SettingsPlaybackRefreshRateSwitchingBehaviorScreen()
	},
	Routes.PLAYBACK_ZOOM_MODE to {
		SettingsPlaybackZoomModeScreen()
	},
	Routes.PLAYBACK_AUDIO_BEHAVIOR to {
		SettingsPlaybackAudioBehaviorScreen()
	},
	Routes.JELLYSEERR to {
		SettingsJellyseerrScreen()
	},
	Routes.JELLYSEERR_ROWS to {
		SettingsJellyseerrRowsScreen()
	},
	Routes.PLUGIN to {
		SettingsPluginScreen()
	},
	Routes.PLUGIN_TOOLBAR to {
		SettingsPluginToolbarScreen()
	},
	Routes.PLUGIN_HOME to {
		SettingsPluginHomeScreen()
	},
	Routes.PLUGIN_MEDIA_BAR to {
		SettingsPluginMediaBarScreen()
	},
	Routes.PLUGIN_THEME_MUSIC to {
		SettingsPluginThemeMusicScreen()
	},
	Routes.PLUGIN_APPEARANCE to {
		SettingsPluginAppearanceScreen()
	},
	Routes.PLUGIN_RATINGS to {
		SettingsPluginRatingsScreen()
	},
	Routes.MOONFIN_NAVBAR_POSITION to {
		SettingsRinzlerNavbarPositionScreen()
	},
	Routes.MOONFIN_SHUFFLE_CONTENT_TYPE to {
		SettingsRinzlerShuffleContentTypeScreen()
	},
	Routes.MOONFIN_MEDIA_BAR_SOURCE_TYPE to {
		SettingsRinzlerMediaBarSourceTypeScreen()
	},
	Routes.MOONFIN_MEDIA_BAR_EXCLUDED_GENRES to {
		SettingsRinzlerMediaBarExcludedGenresScreen()
	},
	Routes.MOONFIN_MEDIA_BAR_CONTENT_TYPE to {
		SettingsRinzlerMediaBarContentTypeScreen()
	},
	Routes.MOONFIN_MEDIA_BAR_ITEM_COUNT to {
		SettingsRinzlerMediaBarItemCountScreen()
	},
	Routes.MOONFIN_MEDIA_BAR_OPACITY to {
		SettingsRinzlerMediaBarOpacityScreen()
	},
	Routes.MOONFIN_MEDIA_BAR_COLOR to {
		SettingsRinzlerMediaBarColorScreen()
	},
	Routes.MOONFIN_THEME_MUSIC_VOLUME to {
		SettingsRinzlerThemeMusicVolumeScreen()
	},
	Routes.MOONFIN_SEASONAL_SURPRISE to {
		SettingsRinzlerSeasonalSurpriseScreen()
	},
	Routes.MOONFIN_HOME_ROWS_IMAGE to {
		SettingsRinzlerHomeRowsImageScreen()
	},
	Routes.MOONFIN_DETAILS_BLUR to {
		SettingsRinzlerDetailsBlurScreen()
	},
	Routes.MOONFIN_BROWSING_BLUR to {
		SettingsRinzlerBrowsingBlurScreen()
	},
	Routes.MOONFIN_PARENTAL_CONTROLS to {
		SettingsRinzlerParentalControlsScreen()
	},
	Routes.MOONFIN_SYNCPLAY to {
		SettingsRinzlerSyncPlayScreen()
	},
	Routes.MOONFIN_SYNCPLAY_MIN_DELAY to {
		SettingsNumericScreen(
			route = Routes.MOONFIN_SYNCPLAY_MIN_DELAY,
			preference = UserPreferences.syncPlayMinDelaySpeedToSync,
			titleRes = R.string.pref_syncplay_min_delay_speed_to_sync,
			valueTemplate = R.string.pref_syncplay_min_delay_speed_to_sync_description,
			minValue = 10.0,
			maxValue = 1000.0,
			stepSize = 10.0,
		)
	},
	Routes.MOONFIN_SYNCPLAY_MAX_DELAY to {
		SettingsNumericScreen(
			route = Routes.MOONFIN_SYNCPLAY_MAX_DELAY,
			preference = UserPreferences.syncPlayMaxDelaySpeedToSync,
			titleRes = R.string.pref_syncplay_max_delay_speed_to_sync,
			valueTemplate = R.string.pref_syncplay_max_delay_speed_to_sync_description,
			minValue = 10.0,
			maxValue = 1000.0,
			stepSize = 10.0,
		)
	},
	Routes.MOONFIN_SYNCPLAY_DURATION to {
		SettingsNumericScreen(
			route = Routes.MOONFIN_SYNCPLAY_DURATION,
			preference = UserPreferences.syncPlaySpeedToSyncDuration,
			titleRes = R.string.pref_syncplay_speed_to_sync_duration,
			valueTemplate = R.string.pref_syncplay_speed_to_sync_duration_description,
			minValue = 500.0,
			maxValue = 5000.0,
			stepSize = 100.0,
		)
	},
	Routes.MOONFIN_SYNCPLAY_MIN_DELAY_SKIP to {
		SettingsNumericScreen(
			route = Routes.MOONFIN_SYNCPLAY_MIN_DELAY_SKIP,
			preference = UserPreferences.syncPlayMinDelaySkipToSync,
			titleRes = R.string.pref_syncplay_min_delay_skip_to_sync,
			valueTemplate = R.string.pref_syncplay_min_delay_skip_to_sync_description,
			minValue = 10.0,
			maxValue = 5000.0,
			stepSize = 10.0,
		)
	},
	Routes.MOONFIN_SYNCPLAY_EXTRA_OFFSET to {
		SettingsNumericScreen(
			route = Routes.MOONFIN_SYNCPLAY_EXTRA_OFFSET,
			preference = UserPreferences.syncPlayExtraTimeOffset,
			titleRes = R.string.pref_syncplay_extra_time_offset,
			valueTemplate = R.string.pref_syncplay_extra_time_offset_description,
			minValue = -1000.0,
			maxValue = 1000.0,
			stepSize = 10.0,
		)
	},
	Routes.SYNCPLAY to {
		SettingsRinzlerSyncPlayScreen()
	},
	Routes.TELEMETRY to {
		SettingsTelemetryScreen()
	},
	Routes.DEVELOPER to {
		SettingsDeveloperScreen()
	},
	Routes.ABOUT to { context ->
		SettingsAboutScreen(context.parameters["fromLogin"] == "true")
	},
	Routes.LICENSES to {
		SettingsLicensesScreen()
	},
	Routes.LICENSE to { context ->
		val artifactId = context.parameters["artifactId"]
		if (artifactId != null) {
			SettingsLicenseScreen(artifactId = artifactId)
		}
	},
)
