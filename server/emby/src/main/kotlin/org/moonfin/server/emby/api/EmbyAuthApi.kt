package uk.rinzler.server.emby.api

import uk.rinzler.server.core.api.ServerAuthApi
import uk.rinzler.server.core.model.AuthResult
import uk.rinzler.server.core.model.QuickConnectInfo
import uk.rinzler.server.core.model.ServerUser
import uk.rinzler.server.emby.EmbyApiClient
import uk.rinzler.server.emby.EmbyUserInfo
import uk.rinzler.server.emby.mapper.toServerUser

class EmbyAuthApi(private val apiClient: EmbyApiClient) : ServerAuthApi {

    override suspend fun authenticateByName(username: String, password: String): AuthResult {
        val result = apiClient.authenticateByName(username, password)
        val user = result.user ?: error("Authentication returned no user")
        return AuthResult(
            accessToken = result.accessToken ?: error("Authentication returned no token"),
            user = user.toServerUser(),
            serverId = result.serverId,
        )
    }

    override suspend fun getCurrentUser(): ServerUser =
        apiClient.validateCurrentUser().toServerUser()

    override suspend fun getPublicUsers(): List<ServerUser> {
        val service = org.emby.client.api.UserServiceApi(apiClient.baseUrl)
        val users = service.getUsersPublic().body()
        return users.map { it.toServerUser() }
    }

    override suspend fun logout() {
        apiClient.logout()
    }

    override suspend fun supportsQuickConnect(): Boolean = false

    override suspend fun initiateQuickConnect(): QuickConnectInfo? =
        throw UnsupportedOperationException("QuickConnect is not supported on Emby")

    override suspend fun checkQuickConnectStatus(secret: String): Boolean =
        throw UnsupportedOperationException("QuickConnect is not supported on Emby")

    override suspend fun authenticateWithQuickConnect(secret: String): AuthResult =
        throw UnsupportedOperationException("QuickConnect is not supported on Emby")
}
