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
import uk.rinzler.tv.ui.base.form.RadioButton
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsLiveTvGuideChannelOrderScreen() {
	val router = LocalRouter.current
	val liveTvPreferences = koinInject<LiveTvPreferences>()
	var channelOrder by rememberPreference(liveTvPreferences, LiveTvPreferences.channelOrder)
	val channelOrderEnum = LiveTvChannelOrder.fromString(channelOrder)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.settings).uppercase()) },
				headingContent = { Text(stringResource(R.string.lbl_sort_by)) },
			)
		}

		items(LiveTvChannelOrder.entries) { entry ->
			ListButton(
				headingContent = { Text(stringResource(entry.nameRes)) },
				trailingContent = { RadioButton(checked = channelOrderEnum == entry) },
				onClick = {
					channelOrder = entry.stringValue
					router.back()
				}
			)
		}
	}
}
