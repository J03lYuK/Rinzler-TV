package uk.rinzler.tv.ui.settings.screen.screensaver

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.RadioButton
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsScreensaverAgeRatingScreen() {
	val router = LocalRouter.current
	val userPreferences = koinInject<UserPreferences>()
	var screensaverAgeRatingMax by rememberPreference(userPreferences, UserPreferences.screensaverAgeRatingMax)
	val options = getScreensaverAgeRatingOptions()

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_screensaver).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_screensaver_ageratingmax)) },
			)
		}

		items(options) { (ageRating, heading) ->
			ListButton(
				headingContent = { Text(heading) },
				trailingContent = { RadioButton(checked = screensaverAgeRatingMax == ageRating) },
				onClick = {
					screensaverAgeRatingMax = ageRating
					router.back()
				}
			)
		}
	}
}
