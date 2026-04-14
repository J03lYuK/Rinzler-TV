package uk.rinzler.tv.ui.home

import android.content.Context
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.Row
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uk.rinzler.tv.R
import uk.rinzler.tv.data.repository.LocalWatchlistRepository
import uk.rinzler.tv.ui.itemhandling.BaseItemDtoBaseRowItem
import uk.rinzler.tv.ui.presentation.CardPresenter
import uk.rinzler.tv.ui.presentation.MutableObjectAdapter
import org.jellyfin.sdk.api.client.ApiClient
import java.util.UUID

class HomeFragmentWatchlistRow(
	private val context: Context,
	private val api: ApiClient,
	private val serverId: UUID,
	private val watchlistRepository: LocalWatchlistRepository
) : HomeFragmentRow {
	private var row: ListRow? = null
	private var listRowAdapter: MutableObjectAdapter<Any>? = null

	override fun addToRowsAdapter(
		context: Context,
		cardPresenter: CardPresenter,
		rowsAdapter: MutableObjectAdapter<Row>
	) {
		listRowAdapter = MutableObjectAdapter(cardPresenter)
		val header = HeaderItem(context.getString(R.string.lbl_watch_list))
		row = ListRow(header, listRowAdapter!!)
		rowsAdapter.add(row!!)

		CoroutineScope(Dispatchers.Main).launch {
			loadWatchlistItems(listRowAdapter!!)
		}
	}

	fun refresh() {
		listRowAdapter?.let { adapter ->
			CoroutineScope(Dispatchers.Main).launch {
				adapter.clear()
				loadWatchlistItems(adapter)
			}
		}
	}

	private suspend fun loadWatchlistItems(adapter: MutableObjectAdapter<Any>) {
		val items = withContext(Dispatchers.IO) {
			watchlistRepository.getWatchlistItems(api, serverId)
		}
		
		items.forEach { item ->
			adapter.add(BaseItemDtoBaseRowItem(item))
		}
	}
}
