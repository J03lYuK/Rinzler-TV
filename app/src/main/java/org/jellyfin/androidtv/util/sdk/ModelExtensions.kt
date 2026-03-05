@file:JvmName("ModelUtils")

package uk.rinzler.tv.util.sdk

import uk.rinzler.tv.auth.model.PublicUser
import uk.rinzler.tv.auth.model.Server
import uk.rinzler.tv.util.apiclient.primaryImage
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo
import org.jellyfin.sdk.model.api.UserDto
import org.jellyfin.sdk.model.serializer.toUUID
import org.jellyfin.sdk.model.serializer.toUUIDOrNull

fun ServerDiscoveryInfo.toServer(): Server = Server(
	id = id.toUUID(),
	name = name,
	address = address,
)

fun UserDto.toPublicUser(): PublicUser? {
	return PublicUser(
		id = id,
		name = name ?: return null,
		serverId = serverId?.toUUIDOrNull() ?: return null,
		accessToken = null,
		imageTag = primaryImage?.tag
	)
}
