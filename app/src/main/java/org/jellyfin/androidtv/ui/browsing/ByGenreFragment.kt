package uk.rinzler.tv.ui.browsing

import uk.rinzler.tv.constant.QueryDefaults
import uk.rinzler.tv.data.repository.ItemRepository
import uk.rinzler.tv.util.apiclient.ioCallContent
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.genresApi
import org.jellyfin.sdk.model.api.BaseItemKind
import org.jellyfin.sdk.model.api.ItemSortBy
import org.jellyfin.sdk.model.api.request.GetItemsRequest
import org.koin.android.ext.android.inject

class ByGenreFragment : BrowseFolderFragment() {
	private val apiClient by inject<ApiClient>()

	override suspend fun setupQueries(rowLoader: RowLoader) {
		val childCount = folder?.childCount ?: 0
		if (childCount <= 0) return

		// Get all genres for this folder
		val genresResponse = apiClient.ioCallContent {
			genresApi.getGenres(
				parentId = folder?.id,
				sortBy = setOf(ItemSortBy.SORT_NAME),
			)
		}

		for (genre in genresResponse.items) {
			val itemsRequest = GetItemsRequest(
				parentId = folder?.id,
				sortBy = setOf(ItemSortBy.SORT_NAME),
				includeItemTypes = includeType?.let(BaseItemKind::fromNameOrNull)?.let(::setOf),
				genres = setOf(genre.name.orEmpty()),
				recursive = true,
				fields = ItemRepository.itemFields,
			)
			rows.add(BrowseRowDef(genre.name, itemsRequest, 40))
		}

		rowLoader.loadRows(rows)
	}
}
