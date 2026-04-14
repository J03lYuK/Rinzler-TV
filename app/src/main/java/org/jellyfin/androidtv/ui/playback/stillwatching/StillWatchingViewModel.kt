package uk.rinzler.tv.ui.playback.stillwatching

import android.content.Context
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.ui.InteractionTrackerViewModel
import uk.rinzler.tv.ui.playback.common.PlaybackPromptViewModel
import org.jellyfin.sdk.api.client.ApiClient

class StillWatchingViewModel(
	context: Context,
	api: ApiClient,
	userPreferences: UserPreferences,
	private val interactionTrackerViewModel: InteractionTrackerViewModel,
) : PlaybackPromptViewModel<StillWatchingState>(
	context,
	api,
	userPreferences,
	initialState = StillWatchingState.INITIALIZED,
	noDataState = StillWatchingState.NO_DATA,
) {
	fun stillWatching() {
		interactionTrackerViewModel.notifyStillWatching()
		setState(StillWatchingState.STILL_WATCHING)
	}

	fun close() {
		setState(StillWatchingState.CLOSE)
	}
}
