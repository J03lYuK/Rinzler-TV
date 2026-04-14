package uk.rinzler.tv.ui.playback.overlay.action

import android.content.Context
import android.view.View
import uk.rinzler.tv.R
import uk.rinzler.tv.ui.livetv.TvManager
import uk.rinzler.tv.ui.playback.PlaybackController
import uk.rinzler.tv.ui.playback.overlay.CustomPlaybackTransportControlGlue
import uk.rinzler.tv.ui.playback.overlay.LeanbackOverlayFragment
import uk.rinzler.tv.ui.playback.overlay.VideoPlayerAdapter as VideoPlayerAdapter

class PreviousLiveTvChannelAction(
	context: Context,
	customPlaybackTransportControlGlue: CustomPlaybackTransportControlGlue,
) : CustomAction(context, customPlaybackTransportControlGlue) {
	init {
		initializeWithIcon(R.drawable.ic_previous_episode)
	}

	@Override
	override fun handleClickAction(
		playbackController: PlaybackController,
		videoPlayerAdapter: VideoPlayerAdapter,
		context: Context,
		view: View,
	) {
		videoPlayerAdapter.masterOverlayFragment.switchChannel(TvManager.getPrevLiveTvChannel())
	}
}
