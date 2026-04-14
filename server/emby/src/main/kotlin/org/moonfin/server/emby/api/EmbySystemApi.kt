package uk.rinzler.server.emby.api

import org.emby.client.api.SystemServiceApi
import uk.rinzler.server.core.api.ServerSystemApi
import uk.rinzler.server.core.model.PublicSystemInfo
import uk.rinzler.server.core.model.SystemInfo
import uk.rinzler.server.emby.EmbyApiClient

class EmbySystemApi(private val apiClient: EmbyApiClient) : ServerSystemApi {

    override suspend fun getPublicSystemInfo(): PublicSystemInfo {
        val service = SystemServiceApi(apiClient.baseUrl)
        val info = service.getSystemInfoPublic().body()
        return PublicSystemInfo(
            serverName = info.serverName ?: "",
            version = info.version ?: "",
            productName = "Emby Server",
            id = info.id ?: "",
            startupWizardCompleted = null,
            localAddress = info.localAddress,
            wanAddress = info.wanAddress,
        )
    }

    override suspend fun getSystemInfo(): SystemInfo {
        val service = SystemServiceApi(apiClient.baseUrl).also {
            apiClient.accessToken?.let { token -> it.setApiKey(token) }
        }
        val info = service.getSystemInfo().body()
        return SystemInfo(
            serverName = info.serverName ?: "",
            version = info.version ?: "",
            productName = "Emby Server",
            id = info.id ?: "",
            localAddress = info.localAddress,
            wanAddress = info.wanAddress,
            operatingSystem = info.operatingSystem,
            httpServerPortNumber = info.httpServerPortNumber,
            httpsPortNumber = info.httpsPortNumber,
            webSocketPortNumber = info.webSocketPortNumber,
            hasPendingRestart = info.hasPendingRestart,
            isShuttingDown = info.isShuttingDown,
            canSelfRestart = info.canSelfRestart,
            canSelfUpdate = info.canSelfUpdate,
            startupWizardCompleted = null,
        )
    }
}
