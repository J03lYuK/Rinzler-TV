package uk.rinzler.server.emby.feature

import uk.rinzler.server.core.feature.ServerFeature
import uk.rinzler.server.core.feature.ServerFeatureSupport

object EmbyFeatureSupport : ServerFeatureSupport {
    override val supportedFeatures: Set<ServerFeature> = setOf(
        ServerFeature.WATCH_PARTY,
        ServerFeature.BIF_TRICKPLAY,
        ServerFeature.EMBY_CONNECT,
        ServerFeature.JELLYSEERR,
    )
}
