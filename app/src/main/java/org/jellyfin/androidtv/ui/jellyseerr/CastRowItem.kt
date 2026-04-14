package uk.rinzler.tv.ui.jellyseerr

import android.content.Context
import uk.rinzler.tv.constant.ImageType
import uk.rinzler.tv.data.service.jellyseerr.JellyseerrCastMemberDto
import uk.rinzler.tv.ui.itemhandling.BaseRowItem
import uk.rinzler.tv.ui.itemhandling.BaseRowType
import uk.rinzler.tv.util.apiclient.JellyfinImage
import uk.rinzler.tv.util.apiclient.JellyfinImageSource
import java.util.UUID

class CastRowItem(
	private val cast: JellyseerrCastMemberDto,
) : BaseRowItem(
	baseRowType = BaseRowType.Person,
	staticHeight = true,
) {
	override fun getImage(imageType: ImageType): JellyfinImage? {
		val profileUrl = cast.profilePath?.let { "https://image.tmdb.org/t/p/w185$it" } ?: return null
		return JellyfinImage(
			item = UUID.randomUUID(),
			source = JellyfinImageSource.ITEM,
			type = org.jellyfin.sdk.model.api.ImageType.PRIMARY,
			tag = profileUrl,
			blurHash = null,
			aspectRatio = 0.67f,
			index = null,
		)
	}

	override fun getFullName(context: Context) = cast.name
	override fun getName(context: Context) = cast.name
	override fun getSubText(context: Context) = cast.character
}
