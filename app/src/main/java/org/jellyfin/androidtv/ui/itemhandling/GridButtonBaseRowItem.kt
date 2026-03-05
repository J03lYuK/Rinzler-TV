package uk.rinzler.tv.ui.itemhandling

import android.content.Context
import uk.rinzler.tv.constant.ImageType
import uk.rinzler.tv.ui.GridButton

class GridButtonBaseRowItem(
	val gridButton: GridButton,
) : BaseRowItem(
	baseRowType = BaseRowType.GridButton,
	staticHeight = true,
) {
	override fun getImage(imageType: ImageType) = null
	override fun getFullName(context: Context) = gridButton.text
	override fun getName(context: Context) = gridButton.text
}
