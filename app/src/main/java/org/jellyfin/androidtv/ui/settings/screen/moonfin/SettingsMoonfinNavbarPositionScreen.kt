package uk.rinzler.tv.ui.settings.screen.moonfin

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.preference.constant.NavbarPosition
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.RadioButton
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsRinzlerNavbarPositionScreen() {
	val router = LocalRouter.current
	val userPreferences = koinInject<UserPreferences>()
	var navbarPosition by rememberPreference(userPreferences, UserPreferences.navbarPosition)

	val options = listOf(
		NavbarPosition.TOP to stringResource(R.string.pref_navbar_position_top),
		NavbarPosition.LEFT to stringResource(R.string.pref_navbar_position_left)
	)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.rinzler_settings).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_navbar_position)) },
				captionContent = { Text(stringResource(R.string.pref_navbar_position_description)) }
			)
		}

		items(options) { (value, label) ->
			ListButton(
				headingContent = { Text(label) },
				trailingContent = { RadioButton(checked = navbarPosition == value) },
				onClick = {
					navbarPosition = value
					router.back()
				}
			)
		}
	}
}
