package uk.rinzler.tv.constant

import uk.rinzler.tv.R
import org.jellyfin.preference.PreferenceEnum

enum class GridDirection(
	override val nameRes: Int,
) : PreferenceEnum {
	/**
	 * Horizontal.
	 */
	HORIZONTAL(R.string.grid_direction_horizontal),

	/**
	 * Vertical.
	 */
	VERTICAL(R.string.grid_direction_vertical),
}
