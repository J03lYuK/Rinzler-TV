package uk.rinzler.tv.ui.settings.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.preference.TelemetryPreferences
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.Checkbox
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import org.koin.compose.koinInject

@Composable
fun SettingsTelemetryScreen() {
	val telemetryPreferences = koinInject<TelemetryPreferences>()

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(stringResource(R.string.settings).uppercase()) },
				headingContent = { Text(stringResource(R.string.pref_telemetry_category)) },
			)
		}

		item {
			var crashReportEnabled by rememberPreference(telemetryPreferences, TelemetryPreferences.crashReportEnabled)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_crash_reports)) },
				trailingContent = { Checkbox(checked = crashReportEnabled) },
				captionContent = {
					if (crashReportEnabled) Text(stringResource(R.string.pref_crash_reports_enabled))
					else Text(stringResource(R.string.pref_crash_reports_disabled))
				},
				onClick = { crashReportEnabled = !crashReportEnabled }
			)
		}

		item {
			var crashReportIncludeLogs by rememberPreference(telemetryPreferences, TelemetryPreferences.crashReportIncludeLogs)
			ListButton(
				headingContent = { Text(stringResource(R.string.pref_crash_report_logs)) },
				trailingContent = { Checkbox(checked = crashReportIncludeLogs) },
				captionContent = {
					if (crashReportIncludeLogs) Text(stringResource(R.string.pref_crash_report_logs_enabled))
					else Text(stringResource(R.string.pref_crash_report_logs_disabled))
				},
				onClick = { crashReportIncludeLogs = !crashReportIncludeLogs }
			)
		}
	}
}
