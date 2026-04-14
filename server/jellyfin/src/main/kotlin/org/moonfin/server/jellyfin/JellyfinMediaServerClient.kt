package uk.rinzler.server.jellyfin

import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.model.ClientInfo
import uk.rinzler.server.core.api.MediaServerClient
import uk.rinzler.server.core.api.ServerAuthApi
import uk.rinzler.server.core.api.ServerDisplayPreferencesApi
import uk.rinzler.server.core.api.ServerImageApi
import uk.rinzler.server.core.api.ServerInstantMixApi
import uk.rinzler.server.core.api.ServerItemsApi
import uk.rinzler.server.core.api.ServerLiveTvApi
import uk.rinzler.server.core.api.ServerPlaybackApi
import uk.rinzler.server.core.api.ServerSessionApi
import uk.rinzler.server.core.api.ServerSystemApi
import uk.rinzler.server.core.api.ServerUserLibraryApi
import uk.rinzler.server.core.api.ServerUserViewsApi
import uk.rinzler.server.core.model.DeviceInfo
import uk.rinzler.server.core.model.ServerType
import org.jellyfin.sdk.model.DeviceInfo as JfDeviceInfo

class JellyfinMediaServerClient(
    private val jellyfinApiClient: ApiClient,
) : MediaServerClient {

    override val serverType: ServerType = ServerType.JELLYFIN

    override val baseUrl: String?
        get() = jellyfinApiClient.baseUrl?.ifEmpty { null }

    override val accessToken: String?
        get() = jellyfinApiClient.accessToken

    override val authApi: ServerAuthApi
        get() = throw UnsupportedOperationException("Use existing Jellyfin auth flow")

    override val itemsApi: ServerItemsApi
        get() = throw UnsupportedOperationException("Not yet migrated to server abstraction")

    override val userLibraryApi: ServerUserLibraryApi
        get() = throw UnsupportedOperationException("Not yet migrated to server abstraction")

    override val playbackApi: ServerPlaybackApi
        get() = throw UnsupportedOperationException("Not yet migrated to server abstraction")

    override val sessionApi: ServerSessionApi
        get() = throw UnsupportedOperationException("Not yet migrated to server abstraction")

    override val imageApi: ServerImageApi
        get() = throw UnsupportedOperationException("Not yet migrated to server abstraction")

    override val systemApi: ServerSystemApi
        get() = throw UnsupportedOperationException("Not yet migrated to server abstraction")

    override val userViewsApi: ServerUserViewsApi
        get() = throw UnsupportedOperationException("Not yet migrated to server abstraction")

    override val liveTvApi: ServerLiveTvApi
        get() = throw UnsupportedOperationException("Not yet migrated to server abstraction")

    override val instantMixApi: ServerInstantMixApi
        get() = throw UnsupportedOperationException("Not yet migrated to server abstraction")

    override val displayPreferencesApi: ServerDisplayPreferencesApi
        get() = throw UnsupportedOperationException("Not yet migrated to server abstraction")

    override fun configure(baseUrl: String, accessToken: String?, userId: String?, deviceInfo: DeviceInfo) {
        jellyfinApiClient.update(
            baseUrl = baseUrl,
            accessToken = accessToken,
            deviceInfo = JfDeviceInfo(id = deviceInfo.id, name = deviceInfo.name),
            clientInfo = ClientInfo(name = deviceInfo.appName, version = deviceInfo.appVersion),
        )
    }

    override fun createForServer(baseUrl: String, accessToken: String?, deviceInfo: DeviceInfo): MediaServerClient {
        jellyfinApiClient.update(baseUrl = baseUrl, accessToken = accessToken)
        return this
    }
}
