package uk.rinzler.tv.ui.settings.compat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import uk.rinzler.tv.ui.base.JellyfinTheme
import uk.rinzler.tv.ui.navigation.ProvideRouter
import uk.rinzler.tv.ui.settings.Routes
import uk.rinzler.tv.ui.settings.composable.SettingsDialog
import uk.rinzler.tv.ui.settings.composable.SettingsRouterContent
import uk.rinzler.tv.ui.settings.routes
import org.koin.compose.viewmodel.koinActivityViewModel

@Composable
fun MainActivitySettings() {
	val viewModel = koinActivityViewModel<SettingsViewModel>()
	val visible by viewModel.visible.collectAsState()

	JellyfinTheme {
		ProvideRouter(routes, Routes.MAIN) {
			SettingsDialog(
				visible = visible,
				onDismissRequest = { viewModel.hide() }
			) {
				SettingsRouterContent()
			}
		}
	}
}
