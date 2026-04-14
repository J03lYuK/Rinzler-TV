package uk.rinzler.tv.ui.itemhandling

import android.content.Context
import uk.rinzler.tv.constant.ImageType
import uk.rinzler.tv.data.model.ChapterItemInfo
import uk.rinzler.tv.util.TimeUtils
import org.jellyfin.sdk.model.extensions.ticks

class ChapterItemInfoBaseRowItem(
	val chapterInfo: ChapterItemInfo,
) : BaseRowItem(
	baseRowType = BaseRowType.Chapter,
	staticHeight = true,
) {
	val serverId: String? get() = chapterInfo.serverId
	override fun getImage(imageType: ImageType) = chapterInfo.image
	override val itemId get() = chapterInfo.itemId
	override fun getFullName(context: Context) = chapterInfo.name
	override fun getName(context: Context) = chapterInfo.name

	override fun getSubText(context: Context) =
		chapterInfo.startPositionTicks.ticks.inWholeMilliseconds.let(TimeUtils::formatMillis)
}
