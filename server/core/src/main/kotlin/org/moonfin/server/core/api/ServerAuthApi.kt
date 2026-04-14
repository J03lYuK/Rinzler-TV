package uk.rinzler.server.core.api

import uk.rinzler.server.core.model.AuthResult
import uk.rinzler.server.core.model.QuickConnectInfo
import uk.rinzler.server.core.model.ServerUser

interface ServerAuthApi {
    suspend fun authenticateByName(username: String, password: String): AuthResult
    suspend fun getCurrentUser(): ServerUser
    suspend fun getPublicUsers(): List<ServerUser>
    suspend fun logout()

    // QuickConnect — implementations for Emby may throw UnsupportedOperationException
    suspend fun supportsQuickConnect(): Boolean
    suspend fun initiateQuickConnect(): QuickConnectInfo?
    suspend fun checkQuickConnectStatus(secret: String): Boolean
    suspend fun authenticateWithQuickConnect(secret: String): AuthResult
}
