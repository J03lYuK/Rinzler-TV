package uk.rinzler.tv.integration.dream.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import uk.rinzler.tv.integration.dream.DreamViewModel
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.preference.constant.ClockBehavior
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun DreamHost() {
	val viewModel = koinViewModel<DreamViewModel>()
	val userPreferences = koinInject<UserPreferences>()
	val content by viewModel.content.collectAsState()

	DreamView(
		content = content,
		showClock = when (userPreferences[UserPreferences.clockBehavior]) {
			ClockBehavior.ALWAYS, ClockBehavior.IN_MENUS -> userPreferences[UserPreferences.screensaverShowClock]
			else -> false
		}
	)
}
