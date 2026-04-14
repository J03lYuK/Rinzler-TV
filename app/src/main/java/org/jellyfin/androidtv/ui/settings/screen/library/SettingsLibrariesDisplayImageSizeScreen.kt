package uk.rinzler.tv.ui.settings.screen.library

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import uk.rinzler.tv.R
import uk.rinzler.tv.constant.PosterSize
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
fun SettingsLibrariesDisplayImageSizeScreen(
	itemId: UUID,
	displayPreferencesId: String,
	serverId: UUID,
	userId: UUID
) {
	val router = LocalRouter.current
	val userView = rememberUserView(itemId)
	val prefs = rememberLibraryPreferences(displayPreferencesId, serverId, userId) ?: return
	var posterSize by rememberPreference(prefs, LibraryPreferences.posterSize)

	SettingsColumn {
		item {
			ListSection(
				overlineContent = { Text(userView?.name.orEmpty().uppercase()) },
				headingContent = { Text(stringResource(R.string.lbl_image_size)) },
			)
		}

		items(PosterSize.entries) { entry ->
			ListButton(
				headingContent = { Text(stringResource(entry.nameRes)) },
				trailingContent = { RadioButton(checked = posterSize == entry) },
				onClick = {
					posterSize = entry
					router.back()
				}
			)
		}
	}
}
