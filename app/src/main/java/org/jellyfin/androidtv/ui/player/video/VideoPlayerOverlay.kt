package uk.rinzler.tv.ui.player.video

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import uk.rinzler.tv.ui.composable.rememberQueueEntry
import uk.rinzler.tv.ui.player.base.PlayerOverlayLayout
import uk.rinzler.tv.ui.player.base.rememberPlayerOverlayVisibility
import uk.rinzler.tv.ui.player.base.toast.MediaToastRegistry
import uk.rinzler.tv.ui.player.base.toast.MediaToasts
import org.jellyfin.playback.core.PlaybackManager
import org.jellyfin.playback.jellyfin.queue.baseItem
import org.jellyfin.playback.jellyfin.queue.baseItemFlow
import org.koin.compose.koinInject

@Composable
fun VideoPlayerOverlay(
	modifier: Modifier = Modifier,
	playbackManager: PlaybackManager = koinInject(),
	mediaToastRegistry: MediaToastRegistry,
) {
	val visibilityState = rememberPlayerOverlayVisibility()

	val entry by rememberQueueEntry(playbackManager)
	val item = entry?.run { baseItemFlow.collectAsState(baseItem) }?.value

	PlayerOverlayLayout(
		visibilityState = visibilityState,
		modifier = modifier,
		header = {
			VideoPlayerHeader(
				item = item,
			)
		},
		controls = {
			VideoPlayerControls(
				playbackManager = playbackManager,
			)
		},
	)

	MediaToasts(mediaToastRegistry)
}
