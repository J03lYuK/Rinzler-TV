package uk.rinzler.playback.emby

import org.jellyfin.playback.core.plugin.playbackPlugin
import org.jellyfin.sdk.model.api.DeviceProfile
import uk.rinzler.playback.emby.mediastream.EmbyMediaStreamResolver
import uk.rinzler.playback.emby.playsession.EmbyPlaySessionService
import uk.rinzler.server.emby.EmbyApiClient

fun embyPlugin(
	api: EmbyApiClient,
	deviceProfileBuilder: () -> DeviceProfile,
) = playbackPlugin {
	provide(EmbyMediaStreamResolver(api, deviceProfileBuilder))
	provide(EmbyPlaySessionService(api))
}
