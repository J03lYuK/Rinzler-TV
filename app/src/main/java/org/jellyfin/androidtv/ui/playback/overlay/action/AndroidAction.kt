package uk.rinzler.tv.ui.playback.overlay.action

import uk.rinzler.tv.ui.playback.overlay.VideoPlayerAdapter

interface AndroidAction {
	fun onActionClicked(
		videoPlayerAdapter: VideoPlayerAdapter
	)
}
