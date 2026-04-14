package uk.rinzler.tv.ui.jellyseerr

import android.graphics.drawable.Drawable
import android.view.View
import androidx.leanback.widget.Row
import uk.rinzler.tv.data.service.jellyseerr.JellyseerrDiscoverItemDto
import uk.rinzler.tv.data.service.jellyseerr.JellyseerrMovieDetailsDto
import uk.rinzler.tv.data.service.jellyseerr.JellyseerrTvDetailsDto

class DetailsOverviewRow(
	val item: JellyseerrDiscoverItemDto,
	var imageDrawable: Drawable? = null,
	val actions: List<View> = emptyList(),
	val movieDetails: JellyseerrMovieDetailsDto? = null,
	val tvDetails: JellyseerrTvDetailsDto? = null,
) : Row()
