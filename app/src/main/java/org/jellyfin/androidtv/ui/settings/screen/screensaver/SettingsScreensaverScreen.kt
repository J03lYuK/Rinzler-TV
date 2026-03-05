package uk.rinzler.tv.ui.settings.screen.screensaver

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.Checkbox
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.Routes
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsScreensaverScreen() {
	val router = LocalRouter.current
	val userPreferences = koinInject<UserPreferences>()

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.settings_title).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_screensaver)) },
			)
		}

		item {
			var screensaverInAppEnabled by rememberPreference(userPreferences, UserPreferences.screensaverInAppEnabled)

			ListButton(
				headingContent = { Text(stringResource(R.string.pref_screensaver_inapp_enabled)) },
				trailingContent = { Checkbox(checked = screensaverInAppEnabled) },
				captionContent = { Text(stringResource(R.string.pref_screensaver_inapp_enabled_description)) },
				onClick = { screensaverInAppEnabled = !screensaverInAppEnabled }
			)
		}

		item {
			var screensaverInAppTimeout by rememberPreference(userPreferences, UserPreferences.screensaverInAppTimeout)
			val caption = getScreensaverTimeoutOptions()
				.firstOrNull { (duration) -> duration.inWholeMilliseconds == screensaverInAppTimeout }
				?.second.orEmpty()

			ListButton(
				headingContent = { Text(stringResource(R.string.pref_screensaver_inapp_timeout)) },
				captionContent = { Text(caption) },
				onClick = { router.push(Routes.CUSTOMIZATION_SCREENSAVER_TIMEOUT) }
			)
		}

		item {
			var screensaverMode by rememberPreference(userPreferences, UserPreferences.screensaverMode)
			val caption = when (screensaverMode) {
				"library" -> stringResource(R.string.pref_screensaver_mode_library)
				"logo" -> stringResource(R.string.pref_screensaver_mode_logo)
				else -> screensaverMode
			}

			ListButton(
				headingContent = { Text(stringResource(R.string.pref_screensaver_mode)) },
				captionContent = { Text(caption) },
				onClick = { router.push(Routes.CUSTOMIZATION_SCREENSAVER_MODE) }
			)
		}

		item {
			var screensaverDimmingLevel by rememberPreference(userPreferences, UserPreferences.screensaverDimmingLevel)
			val caption = if (screensaverDimmingLevel == 0) "Off" else "$screensaverDimmingLevel%"

			ListButton(
				headingContent = { Text(stringResource(R.string.pref_screensaver_dimming)) },
				captionContent = { Text(caption) },
				onClick = { router.push(Routes.CUSTOMIZATION_SCREENSAVER_DIMMING) }
			)
		}

		item {
			var screensaverAgeRatingRequired by rememberPreference(userPreferences, UserPreferences.screensaverAgeRatingRequired)

			ListButton(
				headingContent = { Text(stringResource(R.string.pref_screensaver_ageratingrequired_title)) },
				trailingContent = { Checkbox(checked = screensaverAgeRatingRequired) },
				captionContent = { Text(stringResource(R.string.pref_screensaver_ageratingrequired_enabled)) },
				onClick = { screensaverAgeRatingRequired = !screensaverAgeRatingRequired }
			)
		}

		item {
			var screensaverAgeRatingMax by rememberPreference(userPreferences, UserPreferences.screensaverAgeRatingMax)
			val caption = getScreensaverAgeRatingOptions()
				.firstOrNull { (ageRating) -> ageRating == screensaverAgeRatingMax }
				?.second.orEmpty()

			ListButton(
				headingContent = { Text(stringResource(R.string.pref_screensaver_ageratingmax)) },
				captionContent = { Text(caption) },
				onClick = { router.push(Routes.CUSTOMIZATION_SCREENSAVER_AGE_RATING) }
			)
		}

		item {
			var screensaverShowClock by rememberPreference(userPreferences, UserPreferences.screensaverShowClock)

			ListButton(
				headingContent = { Text(stringResource(R.string.pref_screensaver_show_clock)) },
				trailingContent = { Checkbox(checked = screensaverShowClock) },
				captionContent = { Text(stringResource(R.string.pref_screensaver_show_clock_description)) },
				onClick = { screensaverShowClock = !screensaverShowClock }
			)
		}
	}
}
