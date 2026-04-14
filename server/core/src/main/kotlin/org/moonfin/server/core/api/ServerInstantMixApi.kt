package uk.rinzler.server.core.api

import uk.rinzler.server.core.model.ItemsResult

interface ServerInstantMixApi {
    suspend fun getInstantMix(itemId: String, userId: String? = null, limit: Int? = null): ItemsResult
}
