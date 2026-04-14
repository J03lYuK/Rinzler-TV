package uk.rinzler.tv.ui.itemhandling

import android.content.Context
import uk.rinzler.tv.constant.ImageType
import uk.rinzler.tv.data.service.jellyseerr.JellyseerrCastMemberDto
import uk.rinzler.tv.util.apiclient.JellyfinImage
import uk.rinzler.tv.util.apiclient.JellyfinImageSource
import java.util.UUID

/**
 * BaseRowItem wrapper for Jellyseerr cast members.
 * Renders as a circular person card using TMDB profile URLs.
 */
class JellyseerrPersonBaseRowItem(
	val castMember: JellyseerrCastMemberDto,
) : BaseRowItem(
	baseRowType = BaseRowType.Person,
	staticHeight = true,
) {
	override fun getImage(imageType: ImageType): JellyfinImage? {
		val profilePath = castMember.profilePath ?: return null
		val tmdbUrl = "https://image.tmdb.org/t/p/w185$profilePath"
		return JellyfinImage(
			item = UUID(0, castMember.id.toLong()),
			source = JellyfinImageSource.ITEM,
			type = org.jellyfin.sdk.model.api.ImageType.PRIMARY,
			tag = tmdbUrl,
			blurHash = null,
			aspectRatio = 1f,
			index = null,
		)
	}

	override fun getCardName(context: Context): String? = castMember.name

	override fun getFullName(context: Context): String? = castMember.name

	override fun getName(context: Context): String? = castMember.name

	override fun getSubText(context: Context): String? = castMember.character
}
