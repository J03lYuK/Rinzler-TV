package uk.rinzler.server.core.api

import uk.rinzler.server.core.model.PublicSystemInfo
import uk.rinzler.server.core.model.SystemInfo

interface ServerSystemApi {
    suspend fun getPublicSystemInfo(): PublicSystemInfo
    suspend fun getSystemInfo(): SystemInfo
}
