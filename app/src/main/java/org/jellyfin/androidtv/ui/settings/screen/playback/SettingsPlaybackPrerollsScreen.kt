package uk.rinzler.tv.ui.settings.screen.playback

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
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsPlaybackPrerollsScreen() {
	val userPreferences = koinInject<UserPreferences>()

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_playback).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_playback_prerolls)) },
			)
		}

		item {
			var cinemaModeEnabled by rememberPreference(userPreferences, UserPreferences.cinemaModeEnabled)

			ListButton(
				headingContent = { Text(stringResource(R.string.pref_prerolls_enabled)) },
				trailingContent = { Checkbox(checked = cinemaModeEnabled) },
				captionContent = { Text(stringResource(R.string.pref_prerolls_enabled_description)) },
				onClick = { cinemaModeEnabled = !cinemaModeEnabled }
			)
		}
	}
}
