package uk.rinzler.server.core.api

import kotlinx.coroutines.flow.Flow
import uk.rinzler.server.core.model.DiscoveredServer
import uk.rinzler.server.core.model.ServerValidationResult

interface ServerDiscoveryApi {
    fun discoverLocalServers(): Flow<DiscoveredServer>
    suspend fun getAddressCandidates(input: String): List<String>
    suspend fun validateServer(address: String): ServerValidationResult
}
