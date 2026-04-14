package uk.rinzler.tv.di

import android.content.Context
import android.os.Build
import androidx.lifecycle.ProcessLifecycleOwner
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.network.NetworkFetcher
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.serviceLoaderEnabled
import coil3.svg.SvgDecoder
import coil3.util.Logger
import okio.Path.Companion.toOkioPath
import uk.rinzler.tv.BuildConfig
import uk.rinzler.tv.auth.repository.ServerRepository
import uk.rinzler.tv.auth.repository.UserRepository
import uk.rinzler.tv.auth.repository.UserRepositoryImpl
import uk.rinzler.tv.data.eventhandling.SocketHandler
import uk.rinzler.tv.data.model.DataRefreshService
import uk.rinzler.tv.data.repository.CustomMessageRepository
import uk.rinzler.tv.data.repository.CustomMessageRepositoryImpl
import uk.rinzler.tv.data.repository.ExternalAppRepository
import uk.rinzler.tv.data.repository.ItemMutationRepository
import uk.rinzler.tv.data.repository.ItemMutationRepositoryImpl
import uk.rinzler.tv.data.repository.JellyseerrRepository
import uk.rinzler.tv.data.repository.JellyseerrRepositoryImpl
import uk.rinzler.tv.data.repository.LocalWatchlistRepository
import uk.rinzler.tv.data.repository.MdbListRepository
import uk.rinzler.tv.data.repository.TmdbRepository
import uk.rinzler.tv.data.repository.NotificationsRepository
import uk.rinzler.tv.data.repository.NotificationsRepositoryImpl
import uk.rinzler.tv.data.repository.UserViewsRepository
import uk.rinzler.tv.data.repository.UserViewsRepositoryImpl
import uk.rinzler.tv.data.service.BackgroundService
import uk.rinzler.tv.data.service.UpdateCheckerService
import uk.rinzler.tv.preference.JellyseerrPreferences
import uk.rinzler.tv.data.syncplay.SyncPlayManager
import uk.rinzler.tv.integration.dream.DreamViewModel
import uk.rinzler.tv.ui.InteractionTrackerViewModel
import uk.rinzler.tv.ui.home.mediabar.MediaBarSlideshowViewModel
import uk.rinzler.tv.ui.itemhandling.ItemLauncher
import uk.rinzler.tv.ui.navigation.Destinations
import uk.rinzler.tv.ui.navigation.NavigationRepository
import uk.rinzler.tv.ui.navigation.NavigationRepositoryImpl
import uk.rinzler.tv.ui.shuffle.ShuffleManager
import uk.rinzler.tv.ui.playback.PlaybackControllerContainer
import uk.rinzler.tv.ui.playback.nextup.NextUpViewModel
import uk.rinzler.tv.ui.playback.segment.MediaSegmentRepository
import uk.rinzler.tv.ui.playback.segment.MediaSegmentRepositoryImpl
import uk.rinzler.tv.ui.playback.stillwatching.StillWatchingViewModel
import uk.rinzler.tv.ui.player.photo.PhotoPlayerViewModel
import uk.rinzler.tv.ui.search.SearchFragmentDelegate
import uk.rinzler.tv.ui.search.SearchRepository
import uk.rinzler.tv.ui.search.SearchRepositoryImpl
import uk.rinzler.tv.ui.search.SearchViewModel
import uk.rinzler.tv.ui.syncplay.SyncPlayViewModel
import uk.rinzler.tv.ui.settings.compat.SettingsViewModel
import uk.rinzler.tv.ui.startup.ServerAddViewModel
import uk.rinzler.tv.ui.startup.StartupViewModel
import uk.rinzler.tv.ui.startup.UserLoginViewModel
import uk.rinzler.tv.util.EmbyCompatInterceptor
import uk.rinzler.tv.util.EmbyCacheKeyInterceptor
import uk.rinzler.tv.util.KeyProcessor
import uk.rinzler.tv.util.MarkdownRenderer
import uk.rinzler.tv.util.PlaybackHelper
import uk.rinzler.tv.util.apiclient.ReportingHelper
import uk.rinzler.tv.util.coil.CoilTimberLogger
import uk.rinzler.tv.util.coil.createCoilConnectivityChecker
import uk.rinzler.tv.util.sdk.SdkPlaybackHelper
import org.jellyfin.sdk.android.androidDevice
import org.jellyfin.sdk.api.client.HttpClientOptions
import org.jellyfin.sdk.api.okhttp.OkHttpFactory
import org.jellyfin.sdk.createJellyfin
import org.jellyfin.sdk.model.ClientInfo
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.jellyfin.sdk.Jellyfin as JellyfinSdk

val defaultDeviceInfo = named("defaultDeviceInfo")

val appModule = module {
	// SDK
	single(defaultDeviceInfo) { androidDevice(get()) }
	single { EmbyCompatInterceptor() }
	single {
		val interceptor = get<EmbyCompatInterceptor>()
		val base = okhttp3.OkHttpClient.Builder()
			.addInterceptor(interceptor)
			.build()
		OkHttpFactory(base)
	}
	single { HttpClientOptions() }
	single {
		createJellyfin {
			context = androidContext()

			// Add client info
			val clientName = buildString {
				append("Rinzler Android TV")
				if (BuildConfig.DEBUG) append(" (debug)")
			}
			clientInfo = ClientInfo(clientName, BuildConfig.VERSION_NAME)
			deviceInfo = get(defaultDeviceInfo)

			// Change server version
			minimumServerVersion = ServerRepository.minimumServerVersion

			// Use our own shared factory instance
			apiClientFactory = get<OkHttpFactory>()
			socketConnectionFactory = get<OkHttpFactory>()
		}
	}

	single {
		// Create an empty API instance, the actual values are set by the SessionRepository
		get<JellyfinSdk>().createApi(httpClientOptions = get<HttpClientOptions>())
	}

	single { SocketHandler(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), ProcessLifecycleOwner.get().lifecycle, get(), get(), get()) }

	// Coil (images)
	single {
		val okHttpFactory = get<OkHttpFactory>()
		val httpClientOptions = get<HttpClientOptions>()

		@OptIn(ExperimentalCoilApi::class)
		OkHttpNetworkFetcherFactory(
			callFactory = { okHttpFactory.createClient(httpClientOptions) },
			connectivityChecker = ::createCoilConnectivityChecker,
		)
	}

	single {
		val context = androidContext()
		ImageLoader.Builder(context).apply {
			serviceLoaderEnabled(false)
			logger(CoilTimberLogger(if (BuildConfig.DEBUG) Logger.Level.Warn else Logger.Level.Error))

			// Configure memory cache - use 25% of available memory for images
			memoryCache {
				coil3.memory.MemoryCache.Builder()
					.maxSizePercent(context, percent = 0.25)
					.strongReferencesEnabled(true)
					.build()
			}

			// Configure disk cache - 250MB for image caching
			diskCache {
				coil3.disk.DiskCache.Builder()
					.directory(context.cacheDir.resolve("image_cache").toOkioPath())
					.maxSizeBytes(250L * 1024 * 1024) // 250 MB
					.build()
			}

			components {
				add(EmbyCacheKeyInterceptor())
				add(get<NetworkFetcher.Factory>())

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) add(AnimatedImageDecoder.Factory())
				else add(GifDecoder.Factory())
				add(SvgDecoder.Factory())
			}
		}.build()
	}

	// Non API related
	single { DataRefreshService() }
	single { PlaybackControllerContainer() }
	// Use single scope to ensure the same instance is used across all playback sessions
	single { InteractionTrackerViewModel(get(), get()) }

	single<UserRepository> { UserRepositoryImpl() }
	single<UserViewsRepository> { UserViewsRepositoryImpl(get(), get(), get()) }
	single<NotificationsRepository> { NotificationsRepositoryImpl(get(), get()) }
	single<ItemMutationRepository> { ItemMutationRepositoryImpl(get(), get()) }
	single<CustomMessageRepository> { CustomMessageRepositoryImpl() }
	single<NavigationRepository> { NavigationRepositoryImpl(Destinations.home) }
	single { ShuffleManager(get(), get(), get(), get()) }
	single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
	single<MediaSegmentRepository> { MediaSegmentRepositoryImpl(get(), get(), get(), get()) }
	single<ExternalAppRepository> { ExternalAppRepository(get()) }
	single { LocalWatchlistRepository(androidContext()) }
	single<uk.rinzler.tv.data.repository.MultiServerRepository> { 
		uk.rinzler.tv.data.repository.MultiServerRepositoryImpl(get(), get(), get(), get(), get(defaultDeviceInfo), get(), get()) 
	}
	single { uk.rinzler.tv.util.sdk.ApiClientFactory(get(), get(), get(defaultDeviceInfo), get(), get()) }
	single<uk.rinzler.tv.data.repository.ParentalControlsRepository> {
		uk.rinzler.tv.data.repository.ParentalControlsRepositoryImpl(androidContext(), get(), get())
	}

	// Jellyseerr - Global preferences (server URL, UI settings)
	single(named("global")) { JellyseerrPreferences(androidContext()) }
	// Jellyseerr - User-specific preferences (auth data, API keys) - scoped per user
	factory(named("user")) { (userId: String) -> JellyseerrPreferences(androidContext(), userId) }
	single<JellyseerrRepository> { JellyseerrRepositoryImpl(androidContext(), get(named("global")), get()) }
	single { MdbListRepository(get<OkHttpFactory>().createClient(get()), get()) }
	single { TmdbRepository(get<OkHttpFactory>().createClient(get()), get(), get()) }

	viewModel { StartupViewModel(get(), get(), get(), get()) }
	viewModel { UserLoginViewModel(get(), get(), get(), get(defaultDeviceInfo)) }
	viewModel { ServerAddViewModel(get()) }
	viewModel { NextUpViewModel(get(), get(), get()) }
	viewModel { StillWatchingViewModel(get(), get(), get(), get()) }
	viewModel { PhotoPlayerViewModel(get()) }
	viewModel { SearchViewModel(get(), get(), get(named("global")), get(), get()) }
	viewModel { DreamViewModel(get(), get(), get(), get(), get()) }
	viewModel { SettingsViewModel() }
	viewModel { SyncPlayViewModel() }
	viewModel { uk.rinzler.tv.ui.jellyseerr.JellyseerrViewModel(get()) }
	viewModel { uk.rinzler.tv.ui.itemdetail.v2.ItemDetailsViewModel(get(), get()) }
	viewModel { uk.rinzler.tv.ui.browsing.v2.LibraryBrowseViewModel(get(), get(), get(), get(), get()) }
	viewModel { uk.rinzler.tv.ui.browsing.v2.GenresGridViewModel(get(), get(), get(), get()) }
	viewModel { uk.rinzler.tv.ui.browsing.v2.FavoritesBrowseViewModel(get(), get()) }
	viewModel { uk.rinzler.tv.ui.browsing.v2.MusicBrowseViewModel(get(), get()) }
	viewModel { uk.rinzler.tv.ui.browsing.v2.LiveTvBrowseViewModel(get()) }
	viewModel { uk.rinzler.tv.ui.browsing.v2.RecordingsBrowseViewModel(get()) }
	viewModel { uk.rinzler.tv.ui.browsing.v2.ScheduleBrowseViewModel(get()) }
	viewModel { uk.rinzler.tv.ui.browsing.v2.SeriesRecordingsBrowseViewModel(get()) }
	single { MediaBarSlideshowViewModel(get(), get(), get(), get(), androidContext(), get(), get(), get(), get()) }

	// SyncPlay
	single { SyncPlayManager(androidContext(), get(), get()) }

	single { BackgroundService(get(), get(), get(), get(), get(), get(), get()) }
	single { UpdateCheckerService(get()) }

	single { MarkdownRenderer(get()) }
	single { ItemLauncher() }
	single { KeyProcessor() }
	single { ReportingHelper(get(), get(), get()) }
	single<PlaybackHelper> { SdkPlaybackHelper(get(), get(), get(), get(), get()) }
	single { uk.rinzler.tv.ui.playback.ThemeMusicPlayer(androidContext()) }

	factory { (context: Context) -> 
		SearchFragmentDelegate(context, get(), get(), get()) 
	}
}
