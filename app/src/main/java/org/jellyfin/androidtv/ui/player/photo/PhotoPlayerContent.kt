package uk.rinzler.tv.ui.player.photo

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import uk.rinzler.tv.ui.composable.AsyncImage
import uk.rinzler.tv.util.apiclient.getUrl
import uk.rinzler.tv.util.apiclient.itemImages
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.ImageType
import org.koin.compose.koinInject

@Composable
fun PhotoPlayerContent(
	item: BaseItemDto?,
) {
	val api = koinInject<ApiClient>()
	val resources = LocalResources.current

	AnimatedContent(
		targetState = item,
		transitionSpec = {
			fadeIn() togetherWith fadeOut()
		}
	) { item ->
		val image = item?.itemImages[ImageType.PRIMARY]

		AsyncImage(
			url = image?.getUrl(
				api = api,
				maxWidth = resources.displayMetrics.widthPixels,
				maxHeight = resources.displayMetrics.heightPixels,
			),
			blurHash = image?.blurHash,
			aspectRatio = image?.aspectRatio ?: 1f,
			modifier = Modifier
				.fillMaxSize()
		)
	}
}
