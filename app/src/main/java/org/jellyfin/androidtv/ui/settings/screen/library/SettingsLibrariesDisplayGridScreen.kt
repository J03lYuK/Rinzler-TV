package uk.rinzler.tv.ui.settings.screen.library

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.constant.GridDirection
import uk.rinzler.tv.preference.LibraryPreferences
import uk.rinzler.tv.ui.base.Text
import uk.rinzler.tv.ui.base.form.RadioButton
import uk.rinzler.tv.ui.base.list.ListButton
import uk.rinzler.tv.ui.base.list.ListSection
import uk.rinzler.tv.ui.navigation.LocalRouter
import uk.rinzler.tv.ui.settings.compat.rememberPreference
import uk.rinzler.tv.ui.settings.composable.SettingsColumn
import java.util.UUID

@Composable
fun SettingsLibrariesDisplayGridScreen(
	itemId: UUID,
	displayPreferencesId: String,
	serverId: UUID,
	userId: UUID
) {
	val router = LocalRouter.current
	val userView = rememberUserView(itemId)
	val prefs = rememberLibraryPreferences(displayPreferencesId, serverId, userId) ?: return
	var gridDirection by rememberPreference(prefs, LibraryPreferences.gridDirection)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(userView?.name.orEmpty().uppercase()) },
				headingContent = { Text(stringResource(R.string.grid_direction)) },
			)
		}

		items(GridDirection.entries) { entry ->
			ListButton(
				headingContent = { Text(stringResource(entry.nameRes)) },
				trailingContent = { RadioButton(checked = gridDirection == entry) },
				onClick = {
					gridDirection = entry
					router.back()
				}
			)
		}
	}
}
