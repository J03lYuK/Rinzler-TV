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
fun SettingsRinzlerDetailsBlurScreen() {
	val router = LocalRouter.current
	val userSettingPreferences = koinInject<UserSettingPreferences>()
	var detailsBackgroundBlurAmount by rememberPreference(userSettingPreferences, UserSettingPreferences.detailsBackgroundBlurAmount)

	val options = listOf(
		0 to stringResource(R.string.pref_blur_none),
		5 to stringResource(R.string.pref_blur_light),
		10 to stringResource(R.string.pref_blur_medium),
		15 to stringResource(R.string.pref_blur_strong),
		20 to stringResource(R.string.pref_blur_extra_strong)
	)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_appearance).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_details_background_blur_amount)) },
				captionContent = { Text(stringResource(R.string.pref_details_background_blur_amount_description)) },
			)
		}

		items(options) { (value, label) ->
			ListButton(
				headingContent = { Text(label) },
				trailingContent = { RadioButton(checked = detailsBackgroundBlurAmount == value) },
				onClick = {
					detailsBackgroundBlurAmount = value
					router.back()
				}
			)
		}
	}
}
