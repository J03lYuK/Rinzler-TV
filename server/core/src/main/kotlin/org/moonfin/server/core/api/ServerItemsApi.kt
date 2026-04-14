package uk.rinzler.server.core.api

import uk.rinzler.server.core.model.GetItemsRequest
import uk.rinzler.server.core.model.GetLatestMediaRequest
import uk.rinzler.server.core.model.GetNextUpRequest
import uk.rinzler.server.core.model.GetResumeItemsRequest
import uk.rinzler.server.core.model.ItemsResult
import uk.rinzler.server.core.model.ServerItem

interface ServerItemsApi {
    suspend fun getItems(request: GetItemsRequest): ItemsResult
    suspend fun getResumeItems(request: GetResumeItemsRequest): ItemsResult
    suspend fun getLatestMedia(request: GetLatestMediaRequest): List<ServerItem>
    suspend fun getNextUp(request: GetNextUpRequest): ItemsResult
    suspend fun getSimilarItems(itemId: String, limit: Int? = null): ItemsResult
    suspend fun getSeasons(seriesId: String, userId: String): ItemsResult
    suspend fun getEpisodes(seriesId: String, seasonId: String, userId: String): ItemsResult
}
