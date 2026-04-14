package uk.rinzler.tv.constant

import uk.rinzler.tv.R
import org.jellyfin.preference.PreferenceEnum

/**
 * Content type filter for the shuffle/random button
 */
enum class ShuffleContentType(
	override val serializedName: String,
	override val nameRes: Int,
) : PreferenceEnum {
	MOVIES("movies", R.string.shuffle_content_movies),
	TV_SHOWS("tv_shows", R.string.shuffle_content_tv_shows),
	BOTH("both", R.string.shuffle_content_both),
}
