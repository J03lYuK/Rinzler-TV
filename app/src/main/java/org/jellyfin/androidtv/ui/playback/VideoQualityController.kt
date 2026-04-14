package uk.rinzler.tv.ui.playback

import uk.rinzler.tv.preference.UserPreferences;

class VideoQualityController(
	previousQualitySelection: String,
	private val userPreferences: UserPreferences,
) {
	var currentQuality = previousQualitySelection
		set(value) {
			userPreferences[UserPreferences.maxBitrate] = value
			field = value
		}
}
