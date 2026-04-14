@file:JvmName("ModelUtils")

package uk.rinzler.tv.util.sdk

import uk.rinzler.tv.auth.model.PublicUser
import uk.rinzler.tv.auth.model.Server
import uk.rinzler.tv.util.apiclient.primaryImage
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo
import org.jellyfin.sdk.model.api.UserDto
import org.jellyfin.sdk.model.serializer.toUUID
import org.jellyfin.sdk.model.serializer.toUUIDOrNull
import uk.rinzler.server.core.model.ServerType

fun ServerDiscoveryInfo.toServer(serverType: ServerType = ServerType.JELLYFIN): Server = Server(
	id = id.toUUID(),
	name = name,
	address = address,
	serverType = serverType,
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
