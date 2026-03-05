package uk.rinzler.tv.ui.settings.screen.livetv

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.preference.LiveTvPreferences
import uk.rinzler.tv.preference.constant.LiveTvChannelOrder
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.Checkbox
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.Routes
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsLiveTvGuideOptionsScreen() {
	val router = LocalRouter.current
	val liveTvPreferences = koinInject<LiveTvPreferences>()

	val indicators = listOf(
		rememberPreference(liveTvPreferences, LiveTvPreferences.showHDIndicator) to stringResource(R.string.lbl_hd_programs),
		rememberPreference(liveTvPreferences, LiveTvPreferences.showLiveIndicator) to stringResource(R.string.lbl_live_broadcasts),
		rememberPreference(liveTvPreferences, LiveTvPreferences.showNewIndicator) to stringResource(R.string.lbl_new_episodes),
		rememberPreference(liveTvPreferences, LiveTvPreferences.showPremiereIndicator) to stringResource(R.string.lbl_premieres),
		rememberPreference(liveTvPreferences, LiveTvPreferences.showRepeatIndicator) to stringResource(R.string.lbl_repeat_episodes),
	)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.lbl_live_tv_guide).uppercase()) },
				headingContent = { Text(stringResource(R.string.settings)) },
			)
		}

		item {
			var channelOrder by rememberPreference(liveTvPreferences, LiveTvPreferences.channelOrder)

			ListButton(
				headingContent = { Text(stringResource(R.string.lbl_sort_by)) },
				captionContent = { Text(stringResource(LiveTvChannelOrder.fromString(channelOrder).nameRes)) },
				onClick = { router.push(Routes.LIVETV_GUIDE_CHANNEL_ORDER) }
			)
		}

		item {
			var favsAtTop by rememberPreference(liveTvPreferences, LiveTvPreferences.favsAtTop)

			ListButton(
				headingContent = { Text(stringResource(R.string.lbl_start_favorites)) },
				trailingContent = { Checkbox(checked = favsAtTop) },
				onClick = { favsAtTop = !favsAtTop }
			)
		}

		item {
			var colorCodeGuide by rememberPreference(liveTvPreferences, LiveTvPreferences.colorCodeGuide)

			ListButton(
				headingContent = { Text(stringResource(R.string.lbl_colored_backgrounds)) },
				trailingContent = { Checkbox(checked = colorCodeGuide) },
				onClick = { colorCodeGuide = !colorCodeGuide }
			)
		}

		item { ListSection(headingContent = { Text(stringResource(R.string.lbl_show_indicators)) }) }

		items(indicators) { (preference, label) ->
			var enabled by preference

			ListButton(
				headingContent = { Text(label) },
				trailingContent = { Checkbox(checked = enabled) },
				onClick = { enabled = !enabled }
			)
		}
	}
}
