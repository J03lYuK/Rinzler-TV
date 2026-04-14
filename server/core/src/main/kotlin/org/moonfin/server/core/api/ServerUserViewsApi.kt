package uk.rinzler.server.core.api

import uk.rinzler.server.core.model.ServerItem

interface ServerUserViewsApi {
    suspend fun getUserViews(userId: String): List<ServerItem>
}
