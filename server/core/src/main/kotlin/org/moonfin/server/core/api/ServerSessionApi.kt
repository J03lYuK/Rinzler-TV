package uk.rinzler.server.core.api

import uk.rinzler.server.core.model.ClientCapabilities
import uk.rinzler.server.core.model.SessionInfo

interface ServerSessionApi {
    suspend fun postCapabilities(capabilities: ClientCapabilities)
    suspend fun getSessions(): List<SessionInfo>
}
