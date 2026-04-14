package uk.rinzler.tv.ui.browsing

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import uk.rinzler.tv.R
import uk.rinzler.tv.constant.QueryDefaults
import uk.rinzler.tv.data.repository.ItemRepository
import uk.rinzler.tv.util.apiclient.ioCallContent
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.genresApi
import org.jellyfin.sdk.model.api.BaseItemKind
import org.jellyfin.sdk.model.api.ItemSortBy
import org.jellyfin.sdk.model.api.request.GetItemsRequest
import org.koin.android.ext.android.inject

/**
 * Fragment that displays all genres from all libraries.
 * When a genre is selected, shows all items with that genre across all libraries.
 */
class AllGenresFragment : EnhancedBrowseFragment() {
	private val apiClient by inject<ApiClient>()

	init {
		showViews = false
	}

	override fun setupQueries(rowLoader: RowLoader) {
		lifecycleScope.launch {
			// Get all genres across all libraries (no parentId filter)
			val genresResponse = apiClient.ioCallContent {
				genresApi.getGenres(
					sortBy = setOf(ItemSortBy.SORT_NAME),
				)
			}

			val rows = mutableListOf<BrowseRowDef>()

			for (genre in genresResponse.items) {
				// Create a request for items with this genre from all libraries
				// Only include movies and series, exclude episodes
				val itemsRequest = GetItemsRequest(
					sortBy = setOf(ItemSortBy.SORT_NAME),
					genres = setOf(genre.name.orEmpty()),
					includeItemTypes = setOf(BaseItemKind.MOVIE, BaseItemKind.SERIES),
					recursive = true,
					fields = ItemRepository.itemFields,
				)
				rows.add(BrowseRowDef(genre.name, itemsRequest, 40))
			}

			rowLoader.loadRows(rows)
		}
	}
}
