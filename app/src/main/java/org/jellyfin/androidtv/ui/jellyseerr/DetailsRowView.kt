package uk.rinzler.tv.ui.jellyseerr

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import uk.rinzler.tv.databinding.ViewJellyseerrDetailsRowBinding

class DetailsRowView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
	defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
	val binding = ViewJellyseerrDetailsRowBinding.inflate(LayoutInflater.from(context), this, true)
}
