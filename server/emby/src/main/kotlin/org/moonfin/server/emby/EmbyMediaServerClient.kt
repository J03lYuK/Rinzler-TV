package uk.rinzler.server.emby

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
import uk.rinzler.server.emby.api.EmbyAuthApi
import uk.rinzler.server.emby.api.EmbyDisplayPreferencesApi
import uk.rinzler.server.emby.api.EmbyImageApi
import uk.rinzler.server.emby.api.EmbyInstantMixApi
import uk.rinzler.server.emby.api.EmbyItemsApi
import uk.rinzler.server.emby.api.EmbyLiveTvApi
import uk.rinzler.server.emby.api.EmbyPlaybackApi
import uk.rinzler.server.emby.api.EmbySessionApi
import uk.rinzler.server.emby.api.EmbySystemApi
import uk.rinzler.server.emby.api.EmbyUserLibraryApi
import uk.rinzler.server.emby.api.EmbyUserViewsApi

class EmbyMediaServerClient(
    private var deviceInfo: DeviceInfo,
) : MediaServerClient {

    override val serverType: ServerType = ServerType.EMBY

    private var apiClient: EmbyApiClient = createApiClient(deviceInfo)

    override val baseUrl: String? get() = apiClient.baseUrl.ifEmpty { null }
    override val accessToken: String? get() = apiClient.accessToken

    override val authApi: ServerAuthApi get() = EmbyAuthApi(apiClient)
    override val itemsApi: ServerItemsApi get() = EmbyItemsApi(apiClient)
    override val userLibraryApi: ServerUserLibraryApi get() = EmbyUserLibraryApi(apiClient)
    override val playbackApi: ServerPlaybackApi get() = EmbyPlaybackApi(apiClient)
    override val sessionApi: ServerSessionApi get() = EmbySessionApi(apiClient)
    override val imageApi: ServerImageApi get() = EmbyImageApi(apiClient)
    override val systemApi: ServerSystemApi get() = EmbySystemApi(apiClient)
    override val userViewsApi: ServerUserViewsApi get() = EmbyUserViewsApi(apiClient)
    override val liveTvApi: ServerLiveTvApi get() = EmbyLiveTvApi(apiClient)
    override val instantMixApi: ServerInstantMixApi get() = EmbyInstantMixApi(apiClient)
    override val displayPreferencesApi: ServerDisplayPreferencesApi get() = EmbyDisplayPreferencesApi(apiClient)

    override fun configure(baseUrl: String, accessToken: String?, userId: String?, deviceInfo: DeviceInfo) {
        this.deviceInfo = deviceInfo
        apiClient = createApiClient(deviceInfo)
        apiClient.configure(baseUrl, accessToken, userId)
    }

    override fun createForServer(baseUrl: String, accessToken: String?, deviceInfo: DeviceInfo): MediaServerClient {
        val client = EmbyMediaServerClient(deviceInfo)
        client.apiClient.configure(baseUrl, accessToken, null)
        return client
    }

    private fun createApiClient(info: DeviceInfo) = EmbyApiClient(
        appVersion = info.appVersion,
        clientName = info.appName,
        deviceId = info.id,
        deviceName = info.name,
    )
}
