package uk.rinzler.tv.ui.settings.screen.home

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.constant.HomeSectionType
import uk.rinzler.tv.preference.UserSettingPreferences
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.RadioButton
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListMessage
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsHomeSectionScreen(index: Int) {
	val router = LocalRouter.current
	val userSettingPreferences = koinInject<UserSettingPreferences>()
	val sectionPreference = userSettingPreferences.homesections.getOrNull(index)

	if (sectionPreference == null) {
		ListMessage {
			Text("Unknown section $index")
		}

		return
	}

	var sectionType by rememberPreference(userSettingPreferences, sectionPreference)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.home_prefs).uppercase()) },
				headingContent = { Text(stringResource(R.string.home_section_i, index + 1)) },
			)
		}

		items(HomeSectionType.entries) { entry ->
			ListButton(
				headingContent = { Text(stringResource(entry.nameRes)) },
				trailingContent = { RadioButton(checked = sectionType == entry) },
				onClick = {
					sectionType = entry
					router.back()
				}
			)
		}
	}
}
