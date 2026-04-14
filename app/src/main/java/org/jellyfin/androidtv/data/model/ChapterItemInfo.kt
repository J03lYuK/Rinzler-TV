package uk.rinzler.tv.data.model

import uk.rinzler.tv.util.apiclient.JellyfinImage
import java.util.UUID

data class ChapterItemInfo(
	val itemId: UUID,
	val serverId: String?,
	val name: String?,
	val startPositionTicks: Long,
	val image: JellyfinImage?,
)
