package uk.rinzler.server.jellyfin.feature

import uk.rinzler.server.core.feature.ServerFeature
import uk.rinzler.server.core.feature.ServerFeatureSupport

object JellyfinFeatureSupport : ServerFeatureSupport {
    override val supportedFeatures: Set<ServerFeature> = setOf(
        ServerFeature.QUICK_CONNECT,
        ServerFeature.SYNC_PLAY,
        ServerFeature.MEDIA_SEGMENTS,
        ServerFeature.TRICKPLAY,
        ServerFeature.LYRICS,
        ServerFeature.CLIENT_LOG,
        ServerFeature.JELLYSEERR,
    )
}
