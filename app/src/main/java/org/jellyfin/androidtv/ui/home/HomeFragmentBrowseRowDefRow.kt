package uk.rinzler.tv.ui.home

import android.content.Context
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.Row
import uk.rinzler.tv.constant.ImageType
import uk.rinzler.tv.constant.QueryType
import uk.rinzler.tv.data.querying.GetUserViewsRequest
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.preference.UserSettingPreferences
import uk.rinzler.tv.ui.browsing.BrowseRowDef
import uk.rinzler.tv.ui.itemhandling.ItemRowAdapter
import uk.rinzler.tv.ui.presentation.CardPresenter
import uk.rinzler.tv.ui.presentation.MutableObjectAdapter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeFragmentBrowseRowDefRow(
	private val browseRowDef: BrowseRowDef
) : HomeFragmentRow, KoinComponent {
	private val userPreferences by inject<UserPreferences>()
	private val userSettingPreferences by inject<UserSettingPreferences>()

	override fun addToRowsAdapter(context: Context, cardPresenter: CardPresenter, rowsAdapter: MutableObjectAdapter<Row>) {
		val header = HeaderItem(browseRowDef.headerText)
		val preferParentThumb = userPreferences[UserPreferences.seriesThumbnailsEnabled]

		val imageType = browseRowDef.sectionType?.let {
			userSettingPreferences.getHomeRowImageType(it)
		} ?: ImageType.POSTER

		val presenter = CardPresenter(true, imageType, 150)

		val rowAdapter = when (browseRowDef.queryType) {
			QueryType.NextUp -> ItemRowAdapter(context, browseRowDef.nextUpQuery, preferParentThumb, presenter, rowsAdapter)
			QueryType.LatestItems -> ItemRowAdapter(context, browseRowDef.latestItemsQuery, browseRowDef.chunkSize, userPreferences[UserPreferences.seriesThumbnailsEnabled], presenter, rowsAdapter)
			QueryType.Views -> ItemRowAdapter(context, GetUserViewsRequest, presenter, rowsAdapter)
			QueryType.SimilarSeries -> ItemRowAdapter(context, browseRowDef.similarQuery, QueryType.SimilarSeries, presenter, rowsAdapter)
			QueryType.SimilarMovies -> ItemRowAdapter(context, browseRowDef.similarQuery, QueryType.SimilarMovies, presenter, rowsAdapter)
			QueryType.LiveTvChannel -> ItemRowAdapter(context, browseRowDef.tvChannelQuery, 40, presenter, rowsAdapter)
			QueryType.LiveTvProgram -> ItemRowAdapter(context, browseRowDef.programQuery, presenter, rowsAdapter)
			QueryType.LiveTvRecording -> ItemRowAdapter(context, browseRowDef.recordingQuery, browseRowDef.chunkSize, presenter, rowsAdapter)
			QueryType.Resume -> ItemRowAdapter(context, browseRowDef.resumeQuery, browseRowDef.chunkSize, browseRowDef.preferParentThumb, browseRowDef.isStaticHeight, presenter, rowsAdapter)
			QueryType.MergedContinueWatching -> ItemRowAdapter(context, browseRowDef.resumeQuery, browseRowDef.mergedNextUpQuery, browseRowDef.preferParentThumb, browseRowDef.isStaticHeight, presenter, rowsAdapter)
			else -> ItemRowAdapter(context, browseRowDef.query, browseRowDef.chunkSize, browseRowDef.preferParentThumb, browseRowDef.isStaticHeight, presenter, rowsAdapter, browseRowDef.queryType)
		}

		rowAdapter.setReRetrieveTriggers(browseRowDef.changeTriggers)
		val row = ListRow(header, rowAdapter)
		rowAdapter.setRow(row)
		rowAdapter.Retrieve()
		rowsAdapter.add(row)
	}
}
