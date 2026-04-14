package uk.rinzler.tv.ui.home

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView
import androidx.leanback.widget.RowPresenter
import uk.rinzler.tv.R
import uk.rinzler.tv.ui.home.mediabar.MediaBarSlideshowView
import uk.rinzler.tv.ui.home.mediabar.MediaBarSlideshowViewModel
import uk.rinzler.tv.ui.navigation.Destinations
import uk.rinzler.tv.ui.navigation.NavigationRepository

class MediaBarPresenter(
	private val viewModel: MediaBarSlideshowViewModel,
	private val navigationRepository: NavigationRepository
) : RowPresenter() {
	
	companion object {
		private const val MEDIA_BAR_HEIGHT_DP = 235f
	}
	
	override fun createRowViewHolder(parent: ViewGroup): RowPresenter.ViewHolder {
		val density = parent.context.resources.displayMetrics.density
		val mediaBarHeightPx = (MEDIA_BAR_HEIGHT_DP * density).toInt()
		val rowSpacingPx = parent.context.resources.getDimensionPixelSize(R.dimen.home_row_spacing)
		
		val container = FrameLayout(parent.context).apply {
			layoutParams = ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				rowSpacingPx + mediaBarHeightPx + rowSpacingPx
			)
			setPadding(0, rowSpacingPx, 0, 0)
		}
		
		val composeView = ComposeView(parent.context).apply {
			layoutParams = FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				mediaBarHeightPx
			).apply {
				bottomMargin = rowSpacingPx
			}
			
			setContent {
				MediaBarSlideshowView(
					viewModel = viewModel,
					onItemClick = { item ->
						navigationRepository.navigate(Destinations.itemDetails(item.itemId, item.serverId))
					}
				)
			}
		}
		
		container.addView(composeView)

		return ViewHolder(container)
	}
	
	override fun onBindRowViewHolder(vh: RowPresenter.ViewHolder, item: Any) = Unit
	
	override fun onUnbindRowViewHolder(vh: RowPresenter.ViewHolder) = Unit
	
	class ViewHolder(view: android.view.View) : RowPresenter.ViewHolder(view)
}
