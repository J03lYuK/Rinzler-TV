package uk.rinzler.tv.ui.settings.screen.customization

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.auth.repository.UserRepository
import uk.rinzler.tv.preference.UserSettingPreferences
import uk.rinzler.tv.preference.constant.AppTheme
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.RadioButton
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsCustomizationThemeScreen() {
	val router = LocalRouter.current
	val activity = LocalActivity.current
	val context = LocalContext.current
	val userRepository = koinInject<UserRepository>()
	val userId = userRepository.currentUser.collectAsState().value?.id
	val userSettingPreferences = remember(userId) { UserSettingPreferences(context, userId) }
	var focusColor by rememberPreference(userSettingPreferences, UserSettingPreferences.focusColor)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_customization).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_focus_color)) },
			)
		}

		items(AppTheme.entries) { entry ->
			ListButton(
				headingContent = { Text(stringResource(entry.nameRes)) },
				trailingContent = { RadioButton(checked = focusColor == entry) },
				onClick = {
					if (focusColor != entry) {
						userSettingPreferences[UserSettingPreferences.focusColor] = entry
						focusColor = entry
						activity?.recreate()
					} else {
						router.back()
					}
				}
			)
		}
	}
}
