package uk.rinzler.tv.util

import uk.rinzler.tv.auth.model.Server
import uk.rinzler.server.core.feature.ServerFeature
import uk.rinzler.server.core.feature.ServerFeatureSupport
import uk.rinzler.server.core.model.ServerType
import uk.rinzler.server.emby.feature.EmbyFeatureSupport
import uk.rinzler.server.jellyfin.feature.JellyfinFeatureSupport

fun ServerType.featureSupport(): ServerFeatureSupport = when (this) {
    ServerType.JELLYFIN -> JellyfinFeatureSupport
    ServerType.EMBY -> EmbyFeatureSupport
}

fun Server?.supportsFeature(feature: ServerFeature): Boolean =
    this?.serverType?.featureSupport()?.isSupported(feature) ?: true
