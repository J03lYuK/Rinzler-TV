package uk.rinzler.tv.ui.settings.screen.playback.nextup

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.preference.constant.NextUpBehavior
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.RadioButton
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsPlaybackNextUpBehaviorScreen() {
	val router = LocalRouter.current
	val userPreferences = koinInject<UserPreferences>()
	var nextUpBehavior by rememberPreference(userPreferences, UserPreferences.nextUpBehavior)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_playback_next_up).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_next_up_behavior_title)) },
			)
		}

		items(NextUpBehavior.entries) { entry ->
			ListButton(
				headingContent = { Text(stringResource(entry.nameRes)) },
				trailingContent = { RadioButton(checked = nextUpBehavior == entry) },
				onClick = {
					nextUpBehavior = entry
					router.back()
				}
			)
		}
	}
}
