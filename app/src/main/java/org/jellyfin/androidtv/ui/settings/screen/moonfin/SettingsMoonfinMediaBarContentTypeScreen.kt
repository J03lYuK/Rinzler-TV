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
fun SettingsMoonfinMediaBarContentTypeScreen() {
	val router = LocalRouter.current
	val userSettingPreferences = koinInject<UserSettingPreferences>()
	var mediaBarContentType by rememberPreference(userSettingPreferences, UserSettingPreferences.mediaBarContentType)

	val options = listOf(
		"movies" to stringResource(R.string.pref_shuffle_movies),
		"tv" to stringResource(R.string.pref_shuffle_tv),
		"both" to stringResource(R.string.pref_shuffle_both)
	)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_media_bar_title).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_media_bar_content_type)) },
			)
		}

		items(options) { (value, label) ->
			ListButton(
				headingContent = { Text(label) },
				trailingContent = { RadioButton(checked = mediaBarContentType == value) },
				onClick = {
					mediaBarContentType = value
					router.back()
				}
			)
		}
	}
}
