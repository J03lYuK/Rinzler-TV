package uk.rinzler.tv.di

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
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authModule = module {
	single { AuthenticationStore(get()) }
	single { AuthenticationPreferences(get()) }

	single<AuthenticationRepository> {
		AuthenticationRepositoryImpl(get(), get(), get(), get(), get(), get(defaultDeviceInfo), get(), get(named("global")))
	}
	single<ServerRepository> { ServerRepositoryImpl(get(), get()) }
	single<ServerUserRepository> { ServerUserRepositoryImpl(get(), get()) }
	single<SessionRepository> {
		SessionRepositoryImpl(get(), get(), get(), get(), get(defaultDeviceInfo), get(), get(), get())
	}

	factory {
		val serverRepository = get<ServerRepository>()
		serverRepository.currentServer.value?.serverVersion ?: ServerRepository.minimumServerVersion
	}
}
