package uk.rinzler.tv.ui.browsing

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import uk.rinzler.tv.R
import uk.rinzler.tv.constant.QueryDefaults
import uk.rinzler.tv.constant.QueryType
import uk.rinzler.tv.data.repository.ItemRepository
import uk.rinzler.tv.util.apiclient.ioCallContent
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.itemsApi
import org.jellyfin.sdk.model.api.BaseItemKind
import org.jellyfin.sdk.model.api.ItemSortBy
import org.jellyfin.sdk.model.api.SortOrder
import org.jellyfin.sdk.model.api.request.GetSimilarItemsRequest
import org.koin.android.ext.android.inject

class SuggestedMoviesFragment : EnhancedBrowseFragment() {
	private val api by inject<ApiClient>()

	init {
		showViews = false
	}

	override fun setupQueries(rowLoader: RowLoader) {
		lifecycleScope.launch {
			val response = api.ioCallContent {
				itemsApi.getItems(
					parentId = mFolder.id,
					includeItemTypes = setOf(BaseItemKind.MOVIE),
					sortOrder = setOf(SortOrder.DESCENDING),
					sortBy = setOf(ItemSortBy.DATE_PLAYED),
					limit = 8,
					recursive = true,
				)
			}

			for (item in response.items) {
				val similar = GetSimilarItemsRequest(
					itemId = item.id,
					fields = ItemRepository.itemFields,
					limit = 7,
				)
				mRows.add(BrowseRowDef(getString(R.string.because_you_watched, item.name), similar, QueryType.SimilarMovies))
			}

			rowLoader.loadRows(mRows)
		}
	}
}
