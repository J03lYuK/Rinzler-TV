package uk.rinzler.tv.ui.settings.screen.moonfin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.preference.UserSettingPreferences
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.Routes
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import uk.rinzler.tv.ui.settings.screen.customization.getBlurLabel
import uk.rinzler.tv.ui.settings.screen.customization.getSeasonalLabel
import org.koin.compose.koinInject

@Composable
fun SettingsPluginAppearanceScreen() {
	val router = LocalRouter.current
	val userPreferences = koinInject<UserPreferences>()
	val userSettingPreferences = koinInject<UserSettingPreferences>()

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_plugin_settings).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_appearance)) },
			)
		}

		item {
			val seasonalSurprise by rememberPreference(userPreferences, UserPreferences.seasonalSurprise)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_seasonal_surprise)) },
				captionContent = { Text(getSeasonalLabel(seasonalSurprise)) },
				onClick = { router.push(Routes.MOONFIN_SEASONAL_SURPRISE) }
			)
		}

		item {
			val detailsBlur by rememberPreference(userSettingPreferences, UserSettingPreferences.detailsBackgroundBlurAmount)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_details_background_blur_amount)) },
				captionContent = { Text(getBlurLabel(detailsBlur)) },
				onClick = { router.push(Routes.MOONFIN_DETAILS_BLUR) }
			)
		}

		item {
			val browsingBlur by rememberPreference(userSettingPreferences, UserSettingPreferences.browsingBackgroundBlurAmount)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_browsing_background_blur_amount)) },
				captionContent = { Text(getBlurLabel(browsingBlur)) },
				onClick = { router.push(Routes.MOONFIN_BROWSING_BLUR) }
			)
		}
	}
}
