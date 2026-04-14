package uk.rinzler.tv.ui.settings.screen.playback.mediasegment

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.playback.segment.MediaSegmentRepository
import uk.rinzler.tv.ui.settings.Routes
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsPlaybackMediaSegmentsScreen() {
	val router = LocalRouter.current
	val mediaSegmentRepository = koinInject<MediaSegmentRepository>()

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_playback).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_playback_media_segments)) },
			)
		}

		items(MediaSegmentRepository.SupportedTypes) { segmentType ->
			val action = mediaSegmentRepository.getDefaultSegmentTypeAction(segmentType)

			ListButton(
				headingContent = { Text(stringResource(segmentType.nameRes)) },
				captionContent = { Text(stringResource(action.nameRes)) },
				onClick = {
					router.push(
						route = Routes.PLAYBACK_MEDIA_SEGMENT,
						parameters = mapOf(
							"segmentType" to segmentType.toString(),
						),
					)
				}
			)
		}
	}
}
