package uk.rinzler.tv.ui.settings.screen.moonfin

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.preference.UserSettingPreferences
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.RadioButton
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsRinzlerThemeMusicVolumeScreen() {
	val router = LocalRouter.current
	val userSettingPreferences = koinInject<UserSettingPreferences>()
	var themeMusicVolume by rememberPreference(userSettingPreferences, UserSettingPreferences.themeMusicVolume)

	// 10-100% in 5% increments
	val options = (10..100 step 5).map { it to "$it%" }

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_theme_music_title).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_theme_music_volume)) },
				captionContent = { Text(stringResource(R.string.pref_theme_music_volume_summary)) },
			)
		}

		items(options) { (value, label) ->
			ListButton(
				headingContent = { Text(label) },
				trailingContent = { RadioButton(checked = themeMusicVolume == value) },
				onClick = {
					themeMusicVolume = value
					router.back()
				}
			)
		}
	}
}
