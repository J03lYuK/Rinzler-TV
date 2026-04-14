package uk.rinzler.tv.ui.settings.screen.playback

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.preference.constant.MaxVideoResolution
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.RadioButton
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsPlaybackMaxResolutionScreen() {
	val router = LocalRouter.current
	val userPreferences = koinInject<UserPreferences>()
	var maxVideoResolution by rememberPreference(userPreferences, UserPreferences.maxVideoResolution)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_playback_advanced).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_max_resolution_title)) },
				captionContent = { Text(stringResource(R.string.pref_max_resolution_description)) },
			)
		}

		items(MaxVideoResolution.entries) { resolution ->
			ListButton(
				headingContent = { Text(stringResource(resolution.nameRes)) },
				trailingContent = { RadioButton(checked = maxVideoResolution == resolution) },
				onClick = {
					maxVideoResolution = resolution
					router.back()
				}
			)
		}
	}
}
