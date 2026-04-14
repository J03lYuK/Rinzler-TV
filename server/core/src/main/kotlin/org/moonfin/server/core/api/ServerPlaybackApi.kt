package uk.rinzler.server.core.api

import uk.rinzler.server.core.model.PlaybackInfoRequest
import uk.rinzler.server.core.model.PlaybackInfoResult
import uk.rinzler.server.core.model.PlaybackProgressReport
import uk.rinzler.server.core.model.PlaybackStartReport
import uk.rinzler.server.core.model.PlaybackStopReport
import uk.rinzler.server.core.model.StreamParams

interface ServerPlaybackApi {
    suspend fun getPlaybackInfo(itemId: String, request: PlaybackInfoRequest): PlaybackInfoResult
    fun getVideoStreamUrl(itemId: String, params: StreamParams): String
    fun getAudioStreamUrl(itemId: String, params: StreamParams): String
    suspend fun reportPlaybackStart(info: PlaybackStartReport)
    suspend fun reportPlaybackProgress(info: PlaybackProgressReport)
    suspend fun reportPlaybackStopped(info: PlaybackStopReport)
}
