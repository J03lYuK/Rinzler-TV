package uk.rinzler.tv.ui.itemhandling

import android.content.Context
import uk.rinzler.tv.constant.ImageType
import uk.rinzler.tv.util.apiclient.primaryImage
import org.jellyfin.sdk.model.api.BaseItemPerson

class BaseItemPersonBaseRowItem(
	val person: BaseItemPerson,
) : BaseRowItem(
	baseRowType = BaseRowType.Person,
	staticHeight = true,
) {
	override fun getImage(imageType: ImageType) = person.primaryImage
	override val itemId get() = person.id
	override fun getFullName(context: Context) = person.name
	override fun getName(context: Context) = person.name
	override fun getSubText(context: Context) = person.role
}
