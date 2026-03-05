package uk.rinzler.tv.ui.settings.screen.screensaver

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
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsScreensaverModeScreen() {
	val userPreferences = koinInject<UserPreferences>()

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.settings_title).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_screensaver_mode)) },
			)
		}

		item {
			var screensaverMode by rememberPreference(userPreferences, UserPreferences.screensaverMode)

			ListButton(
				headingContent = { Text(stringResource(R.string.pref_screensaver_mode_library)) },
				trailingContent = { RadioButton(checked = screensaverMode == "library") },
				onClick = { screensaverMode = "library" }
			)
		}

		item {
			var screensaverMode by rememberPreference(userPreferences, UserPreferences.screensaverMode)

			ListButton(
				headingContent = { Text(stringResource(R.string.pref_screensaver_mode_logo)) },
				trailingContent = { RadioButton(checked = screensaverMode == "logo") },
				onClick = { screensaverMode = "logo" }
			)
		}
	}
}
