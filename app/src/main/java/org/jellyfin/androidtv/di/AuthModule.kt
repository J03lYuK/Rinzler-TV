package uk.rinzler.tv.di

import android.content.Context
import android.media.AudioManager
import uk.rinzler.tv.BuildConfig
import uk.rinzler.tv.auth.repository.AuthenticationRepository
import uk.rinzler.tv.auth.repository.AuthenticationRepositoryImpl
import uk.rinzler.tv.auth.repository.ServerRepository
import uk.rinzler.tv.auth.repository.ServerRepositoryImpl
import uk.rinzler.tv.auth.repository.ServerUserRepository
import uk.rinzler.tv.auth.repository.ServerUserRepositoryImpl
import uk.rinzler.tv.auth.repository.SessionRepository
import uk.rinzler.tv.auth.repository.SessionRepositoryImpl
import uk.rinzler.tv.auth.store.AuthenticationPreferences
import uk.rinzler.tv.auth.store.AuthenticationStore
import org.jellyfin.sdk.model.DeviceInfo
import org.koin.core.qualifier.named
import org.koin.dsl.module
import uk.rinzler.server.emby.EmbyApiClient
import uk.rinzler.server.emby.socket.EmbyWebSocketClient

val authModule = module {
	single { AuthenticationStore(get()) }
	single { AuthenticationPreferences(get()) }

	single {
		val deviceInfo = get<DeviceInfo>(defaultDeviceInfo)
		EmbyApiClient(
			appVersion = BuildConfig.VERSION_NAME,
			clientName = "Rinzler Android TV",
			deviceId = deviceInfo.id,
			deviceName = deviceInfo.name,
		)
	}

	single {
		val context = get<Context>()
		val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
		EmbyWebSocketClient(
			api = get<EmbyApiClient>(),
			audioManager = audioManager,
		)
	}

	single<AuthenticationRepository> {
		AuthenticationRepositoryImpl(get(), get(), get(), get(), get(), get(defaultDeviceInfo), get(), get(named("global")), get())
	}
	single<ServerRepository> { ServerRepositoryImpl(get(), get()) }
	single<ServerUserRepository> { ServerUserRepositoryImpl(get(), get(), get()) }
	single<SessionRepository> {
		SessionRepositoryImpl(get(), get(), get(), get(), get(defaultDeviceInfo), get(), get(), get(), get(), get(), get())
	}

	factory {
		val serverRepository = get<ServerRepository>()
		serverRepository.currentServer.value?.serverVersion ?: ServerRepository.minimumServerVersion
	}
}
