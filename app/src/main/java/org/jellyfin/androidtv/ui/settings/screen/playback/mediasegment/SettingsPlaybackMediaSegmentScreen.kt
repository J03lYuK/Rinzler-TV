package uk.rinzler.tv.ui.settings.screen.playback.mediasegment

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.RadioButton
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.playback.segment.MediaSegmentAction
import uk.rinzler.tv.ui.playback.segment.MediaSegmentRepository
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.jellyfin.sdk.model.api.MediaSegmentType
import org.koin.compose.koinInject

@Composable
fun SettingsPlaybackMediaSegmentScreen(
	segmentType: MediaSegmentType
) {
	val router = LocalRouter.current
	val mediaSegmentRepository = koinInject<MediaSegmentRepository>()
	val action = mediaSegmentRepository.getDefaultSegmentTypeAction(segmentType)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_playback_media_segments).uppercase()) },
				headingContent = { Text(stringResource(segmentType.nameRes)) },
			)
		}

		items(MediaSegmentAction.entries) { entry ->
			ListButton(
				headingContent = { Text(stringResource(entry.nameRes)) },
				trailingContent = { RadioButton(checked = action == entry) },
				onClick = {
					mediaSegmentRepository.setDefaultSegmentTypeAction(segmentType, entry)
					router.back()
				}
			)
		}
	}
}
