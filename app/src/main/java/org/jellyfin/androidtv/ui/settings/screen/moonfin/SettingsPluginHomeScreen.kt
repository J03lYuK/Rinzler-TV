package uk.rinzler.tv.ui.settings.screen.moonfin

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
fun SettingsPluginHomeScreen() {
	val userPreferences = koinInject<UserPreferences>()

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_plugin_settings).uppercase()) },
				headingContent = { Text(stringResource(R.string.home_section_settings)) },
			)
		}

		item {
			var mergeContinueWatchingNextUp by rememberPreference(userPreferences, UserPreferences.mergeContinueWatchingNextUp)
			ListButton(
				headingContent = { Text(stringResource(R.string.lbl_merge_continue_watching_next_up)) },
				captionContent = { Text(stringResource(R.string.lbl_merge_continue_watching_next_up_description)) },
				trailingContent = { Checkbox(checked = mergeContinueWatchingNextUp) },
				onClick = { mergeContinueWatchingNextUp = !mergeContinueWatchingNextUp }
			)
		}

		item {
			var enableMultiServerLibraries by rememberPreference(userPreferences, UserPreferences.enableMultiServerLibraries)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_multi_server_libraries)) },
				captionContent = { Text(stringResource(R.string.pref_multi_server_libraries_description)) },
				trailingContent = { Checkbox(checked = enableMultiServerLibraries) },
				onClick = { enableMultiServerLibraries = !enableMultiServerLibraries }
			)
		}

		item {
			var enableFolderView by rememberPreference(userPreferences, UserPreferences.enableFolderView)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_enable_folder_view)) },
				captionContent = { Text(stringResource(R.string.pref_enable_folder_view_description)) },
				trailingContent = { Checkbox(checked = enableFolderView) },
				onClick = { enableFolderView = !enableFolderView }
			)
		}

		item {
			var confirmExit by rememberPreference(userPreferences, UserPreferences.confirmExit)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_confirm_exit)) },
				captionContent = { Text(stringResource(R.string.pref_confirm_exit_description)) },
				trailingContent = { Checkbox(checked = confirmExit) },
				onClick = { confirmExit = !confirmExit }
			)
		}
	}
}
