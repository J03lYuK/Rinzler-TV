package uk.rinzler.tv.constant

import uk.rinzler.tv.R
import org.jellyfin.preference.PreferenceEnum

enum class PosterSize(
	override val nameRes: Int,
) : PreferenceEnum {
	/**
	 * Smallest.
	 */
	SMALLEST(R.string.image_size_smallest),
	/**
	 * Small.
	 */
	SMALL(R.string.image_size_small),

	/**
	 * Medium.
	 */
	MED(R.string.image_size_medium),

	/**
	 * Large.
	 */
	LARGE(R.string.image_size_large),

	/**
	 * Extra Large.
	 */
	X_LARGE(R.string.image_size_xlarge),
}
