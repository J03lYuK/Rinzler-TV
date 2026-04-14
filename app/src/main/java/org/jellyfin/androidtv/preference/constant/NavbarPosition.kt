package uk.rinzler.tv.preference.constant

import uk.rinzler.tv.R
import org.jellyfin.preference.PreferenceEnum

enum class NavbarPosition(
	override val serializedName: String,
	override val nameRes: Int,
) : PreferenceEnum {
	TOP("top", R.string.pref_navbar_position_top),
	LEFT("left", R.string.pref_navbar_position_left),
}
