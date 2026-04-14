package uk.rinzler.server.core.api

import kotlinx.coroutines.flow.Flow
import uk.rinzler.server.core.model.ServerWebSocketMessage

interface ServerWebSocketApi {
    suspend fun connect()
    suspend fun disconnect()
    val messages: Flow<ServerWebSocketMessage>
}
