package uk.rinzler.tv.ui.settings.screen.moonfin

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
fun SettingsRinzlerSeasonalSurpriseScreen() {
	val router = LocalRouter.current
	val userPreferences = koinInject<UserPreferences>()
	var seasonalSurprise by rememberPreference(userPreferences, UserPreferences.seasonalSurprise)

	val options = listOf(
		"none" to stringResource(R.string.pref_seasonal_none),
		"winter" to stringResource(R.string.pref_seasonal_winter),
		"spring" to stringResource(R.string.pref_seasonal_spring),
		"summer" to stringResource(R.string.pref_seasonal_summer),
		"halloween" to stringResource(R.string.pref_seasonal_halloween),
		"fall" to stringResource(R.string.pref_seasonal_fall)
	)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_appearance).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_seasonal_surprise)) },
			)
		}

		items(options) { (value, label) ->
			ListButton(
				headingContent = { Text(label) },
				trailingContent = { RadioButton(checked = seasonalSurprise == value) },
				onClick = {
					seasonalSurprise = value
					router.back()
				}
			)
		}
	}
}
