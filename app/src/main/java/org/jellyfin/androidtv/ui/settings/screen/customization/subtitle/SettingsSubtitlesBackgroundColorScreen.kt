package uk.rinzler.tv.ui.settings.screen.customization.subtitle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import uk.rinzler.tv.ui.settings.screen.customization.subtitle.composable.SubtitleColorPresetsControl
import uk.rinzler.tv.ui.settings.screen.customization.subtitle.composable.SubtitleStylePreview
import uk.rinzler.tv.ui.settings.util.ListColorChannelRangeControl
import org.koin.compose.koinInject

@Composable
fun SettingsSubtitlesBackgroundColorScreen() {
	val userPreferences = koinInject<UserPreferences>()

	var subtitlesBackgroundColor by rememberPreference(userPreferences, UserPreferences.subtitlesBackgroundColor)
	val colorValue = Color(subtitlesBackgroundColor).convert(ColorSpaces.Srgb)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.pref_subtitles).uppercase()) },
				headingContent = { Text(stringResource(R.string.lbl_subtitle_background_color)) },
			)
		}

		item {
			SubtitleStylePreview(
				userPreferences = userPreferences,
				subtitlesBackgroundColor = subtitlesBackgroundColor,
			)
		}

		// Presets
		item { ListSection(headingContent = { Text(stringResource(R.string.color_presets)) }) }

		item {
			SubtitleColorPresetsControl(
				presets = SubtitleBackgroundColorPresets,
				value = colorValue,
				onValueChange = { subtitlesBackgroundColor = it.toArgb().toLong() }
			)
		}

		// Color channels

		item { ListSection(headingContent = { Text(stringResource(R.string.color_custom)) }) }

		item {
			ListColorChannelRangeControl(
				headingContent = { Text(stringResource(R.string.color_red)) },
				channel = Color.Red,
				value = colorValue,
				onValueChange = { subtitlesBackgroundColor = it.toArgb().toLong() }
			)
		}

		item {
			ListColorChannelRangeControl(
				headingContent = { Text(stringResource(R.string.color_green)) },
				channel = Color.Green,
				value = colorValue,
				onValueChange = { subtitlesBackgroundColor = it.toArgb().toLong() }
			)
		}

		item {
			ListColorChannelRangeControl(
				headingContent = { Text(stringResource(R.string.color_blue)) },
				channel = Color.Blue,
				value = colorValue,
				onValueChange = { subtitlesBackgroundColor = it.toArgb().toLong() }
			)
		}

		item {
			ListColorChannelRangeControl(
				headingContent = { Text(stringResource(R.string.color_alpha)) },
				channel = Color.Transparent,
				value = colorValue,
				onValueChange = { subtitlesBackgroundColor = it.toArgb().toLong() }
			)
		}
	}
}
