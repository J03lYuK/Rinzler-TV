package uk.rinzler.tv.ui.home

import android.content.Context
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.Row
import uk.rinzler.tv.R
import uk.rinzler.tv.data.querying.GetUserViewsRequest
import uk.rinzler.tv.ui.itemhandling.ItemRowAdapter
import uk.rinzler.tv.ui.presentation.CardPresenter
import uk.rinzler.tv.ui.presentation.MutableObjectAdapter

class HomeFragmentViewsRow(
	val small: Boolean,
) : HomeFragmentRow {
	private companion object {
		val smallCardPresenter = CardPresenter(true, 75)
		// Use staticHeight=150 so the card height follows the user's poster size preference
		val largeCardPresenter = CardPresenter(true, 150)
	}

	override fun addToRowsAdapter(context: Context, cardPresenter: CardPresenter, rowsAdapter: MutableObjectAdapter<Row>) {
		val presenter = if (small) smallCardPresenter else largeCardPresenter
		val rowAdapter = ItemRowAdapter(context, GetUserViewsRequest, presenter, rowsAdapter)

		val header = HeaderItem(context.getString(R.string.lbl_my_media))
		val row = ListRow(header, rowAdapter)
		rowAdapter.setRow(row)
		rowAdapter.Retrieve()
		rowsAdapter.add(row)
	}
}
