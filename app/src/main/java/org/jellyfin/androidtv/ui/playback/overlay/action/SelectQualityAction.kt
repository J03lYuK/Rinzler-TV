package uk.rinzler.tv.ui.playback.overlay.action

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import uk.rinzler.tv.R
import uk.rinzler.tv.constant.getQualityProfiles
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.ui.playback.PlaybackController
import uk.rinzler.tv.ui.playback.VideoQualityController
import uk.rinzler.tv.ui.playback.overlay.CustomPlaybackTransportControlGlue
import uk.rinzler.tv.ui.playback.overlay.VideoPlayerAdapter

class SelectQualityAction(
	context: Context,
	customPlaybackTransportControlGlue: CustomPlaybackTransportControlGlue,
	userPreferences: UserPreferences
) : CustomAction(context, customPlaybackTransportControlGlue) {
	private val previousQualitySelection = userPreferences[UserPreferences.maxBitrate]
	private val qualityController = VideoQualityController(previousQualitySelection, userPreferences)
	private val qualityProfiles = getQualityProfiles(context)
	private var popup: PopupMenu? = null

	init {
		initializeWithIcon(R.drawable.ic_select_quality)
	}

	override fun handleClickAction(
		playbackController: PlaybackController,
		videoPlayerAdapter: VideoPlayerAdapter,
		context: Context,
		view: View,
	) {
		videoPlayerAdapter.leanbackOverlayFragment.setFading(false)
		dismissPopup()
		popup = PopupMenu(context, view, Gravity.END).apply {
			qualityProfiles.values.forEachIndexed { i, selected ->
				menu.add(0, i, i, selected)
			}

			menu.setGroupCheckable(0, true, true)
			val currentQualityIndex = qualityProfiles.keys.indexOf(qualityController.currentQuality)
			if (currentQualityIndex != -1) {
				menu.getItem(currentQualityIndex)?.let { item ->
					item.isChecked = true
				}
			}

			setOnDismissListener {
				videoPlayerAdapter.leanbackOverlayFragment.setFading(true)
				popup = null
			}
			setOnMenuItemClickListener { menuItem ->
				qualityController.currentQuality = qualityProfiles.keys.elementAt(menuItem.itemId)
				playbackController.refreshStream()
				dismiss()
				true
			}
		}
		popup?.show()
	}

	fun dismissPopup() {
		popup?.dismiss()
	}
}
