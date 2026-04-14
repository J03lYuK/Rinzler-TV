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
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.Routes
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import uk.rinzler.tv.ui.settings.screen.customization.getShuffleContentTypeLabel
import org.koin.compose.koinInject

@Composable
fun SettingsPluginToolbarScreen() {
	val router = LocalRouter.current
	val userPreferences = koinInject<UserPreferences>()

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_plugin_settings).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_toolbar_customization)) },
			)
		}

		item {
			val navbarPosition by rememberPreference(userPreferences, UserPreferences.navbarPosition)
			val navbarLabel = when (navbarPosition) {
				uk.rinzler.tv.preference.constant.NavbarPosition.TOP -> stringResource(R.string.pref_navbar_position_top)
				uk.rinzler.tv.preference.constant.NavbarPosition.LEFT -> stringResource(R.string.pref_navbar_position_left)
			}
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_navbar_position)) },
				captionContent = { Text(navbarLabel) },
				onClick = { router.push(Routes.MOONFIN_NAVBAR_POSITION) }
			)
		}

		item {
			var showShuffleButton by rememberPreference(userPreferences, UserPreferences.showShuffleButton)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_show_shuffle_button)) },
				captionContent = { Text(stringResource(R.string.pref_show_shuffle_button_description)) },
				trailingContent = { Checkbox(checked = showShuffleButton) },
				onClick = { showShuffleButton = !showShuffleButton }
			)
		}

		item {
			var showGenresButton by rememberPreference(userPreferences, UserPreferences.showGenresButton)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_show_genres_button)) },
				captionContent = { Text(stringResource(R.string.pref_show_genres_button_description)) },
				trailingContent = { Checkbox(checked = showGenresButton) },
				onClick = { showGenresButton = !showGenresButton }
			)
		}

		item {
			var showFavoritesButton by rememberPreference(userPreferences, UserPreferences.showFavoritesButton)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_show_favorites_button)) },
				captionContent = { Text(stringResource(R.string.pref_show_favorites_button_description)) },
				trailingContent = { Checkbox(checked = showFavoritesButton) },
				onClick = { showFavoritesButton = !showFavoritesButton }
			)
		}

		item {
			var showLibrariesInToolbar by rememberPreference(userPreferences, UserPreferences.showLibrariesInToolbar)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_show_libraries_in_toolbar)) },
				captionContent = { Text(stringResource(R.string.pref_show_libraries_in_toolbar_description)) },
				trailingContent = { Checkbox(checked = showLibrariesInToolbar) },
				onClick = { showLibrariesInToolbar = !showLibrariesInToolbar }
			)
		}

		item {
			val shuffleContentType by rememberPreference(userPreferences, UserPreferences.shuffleContentType)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_shuffle_content_type)) },
				captionContent = { Text(getShuffleContentTypeLabel(shuffleContentType)) },
				onClick = { router.push(Routes.MOONFIN_SHUFFLE_CONTENT_TYPE) }
			)
		}
	}
}
