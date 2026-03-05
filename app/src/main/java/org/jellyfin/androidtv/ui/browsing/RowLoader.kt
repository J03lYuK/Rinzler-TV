package uk.rinzler.tv.ui.browsing

interface RowLoader {
	fun loadRows(rows: MutableList<BrowseRowDef>)
}
