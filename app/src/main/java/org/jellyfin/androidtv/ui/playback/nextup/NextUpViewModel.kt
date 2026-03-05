package uk.rinzler.tv.ui.playback.nextup

import android.content.Context
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.ui.playback.common.PlaybackPromptViewModel
import org.jellyfin.sdk.api.client.ApiClient

class NextUpViewModel(
	context: Context,
	api: ApiClient,
	userPreferences: UserPreferences,
) : PlaybackPromptViewModel<NextUpState>(
	context,
	api,
	userPreferences,
	initialState = NextUpState.INITIALIZED,
	noDataState = NextUpState.NO_DATA,
) {
	fun playNext() {
		setState(NextUpState.PLAY_NEXT)
	}

	fun close() {
		setState(NextUpState.CLOSE)
	}
}
