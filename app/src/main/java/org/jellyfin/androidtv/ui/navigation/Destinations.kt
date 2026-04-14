package uk.rinzler.tv.ui.navigation

import kotlinx.serialization.json.Json
import uk.rinzler.tv.constant.Extras
import uk.rinzler.tv.ui.browsing.v2.FavoritesBrowseFragment
import uk.rinzler.tv.ui.browsing.ByLetterFragment
import uk.rinzler.tv.ui.browsing.CollectionFragment
import uk.rinzler.tv.ui.browsing.FolderViewFragment
import uk.rinzler.tv.ui.browsing.GenericFolderFragment
import uk.rinzler.tv.ui.browsing.SuggestedMoviesFragment
import uk.rinzler.tv.ui.browsing.v2.GenresGridV2Fragment
import uk.rinzler.tv.ui.browsing.v2.LibraryBrowseFragment
import uk.rinzler.tv.ui.browsing.v2.LiveTvBrowseFragment
import uk.rinzler.tv.ui.browsing.v2.MusicBrowseFragment
import uk.rinzler.tv.ui.browsing.v2.RecordingsBrowseFragment
import uk.rinzler.tv.ui.browsing.v2.ScheduleBrowseFragment
import uk.rinzler.tv.ui.browsing.v2.SeriesRecordingsBrowseFragment
import uk.rinzler.tv.ui.home.HomeFragment
import uk.rinzler.tv.ui.itemdetail.FullDetailsFragment
import uk.rinzler.tv.ui.itemdetail.ItemListFragment
import uk.rinzler.tv.ui.itemdetail.v2.ItemDetailsFragment
import uk.rinzler.tv.ui.itemdetail.v2.TrailerPlayerFragment
import uk.rinzler.tv.ui.itemdetail.MusicFavoritesListFragment
import uk.rinzler.tv.ui.jellyseerr.BrowseFilterType
import uk.rinzler.tv.ui.jellyseerr.DiscoverFragment
import uk.rinzler.tv.ui.jellyseerr.JellyseerrBrowseByFragment
import uk.rinzler.tv.ui.jellyseerr.MediaDetailsFragment
import uk.rinzler.tv.ui.jellyseerr.PersonDetailsFragment
import uk.rinzler.tv.ui.jellyseerr.RequestsFragment
import uk.rinzler.tv.ui.jellyseerr.SettingsFragment as JellyseerrSettingsFragment
import uk.rinzler.tv.ui.livetv.LiveTvGuideFragment
import uk.rinzler.tv.ui.playback.AudioNowPlayingFragment
import uk.rinzler.tv.ui.playback.CustomPlaybackOverlayFragment
import uk.rinzler.tv.ui.playback.nextup.NextUpFragment
import uk.rinzler.tv.ui.playback.stillwatching.StillWatchingFragment
import uk.rinzler.tv.ui.player.photo.PhotoPlayerFragment
import uk.rinzler.tv.ui.player.video.VideoPlayerFragment
import uk.rinzler.tv.ui.search.SearchFragment
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.ItemSortBy
import org.jellyfin.sdk.model.api.SeriesTimerInfoDto
import org.jellyfin.sdk.model.api.SortOrder
import java.util.UUID

@Suppress("TooManyFunctions")
object Destinations {
	// General
	val home = fragmentDestination<HomeFragment>()
	fun search(query: String? = null) = fragmentDestination<SearchFragment>(
		SearchFragment.EXTRA_QUERY to query,
	)

	// Browsing
	// TODO only pass item id instead of complete JSON to browsing destinations
	@JvmOverloads
	fun libraryBrowser(item: BaseItemDto, serverId: UUID? = null, userId: UUID? = null) = fragmentDestination<LibraryBrowseFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
		"ServerId" to serverId?.toString(),
		"UserId" to userId?.toString(),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	@JvmName("libraryBrowserWithType")
	fun libraryBrowser(item: BaseItemDto, includeType: String) =
		fragmentDestination<LibraryBrowseFragment>(
			Extras.Folder to Json.Default.encodeToString(item),
			Extras.IncludeType to includeType,
		)

	fun liveTvBrowser(item: BaseItemDto) = fragmentDestination<LiveTvBrowseFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
	)

	@JvmOverloads
	fun musicBrowser(item: BaseItemDto, serverId: UUID? = null, userId: UUID? = null) = fragmentDestination<MusicBrowseFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
		"ServerId" to serverId?.toString(),
		"UserId" to userId?.toString(),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	@JvmOverloads
	fun collectionBrowser(item: BaseItemDto, serverId: UUID? = null, userId: UUID? = null) = fragmentDestination<CollectionFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
		"ServerId" to serverId?.toString(),
		"UserId" to userId?.toString(),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	@JvmOverloads
	fun folderBrowser(item: BaseItemDto, serverId: UUID? = null, userId: UUID? = null) = fragmentDestination<GenericFolderFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
		"ServerId" to serverId?.toString(),
		"UserId" to userId?.toString(),
	)

	// All genres across all libraries (new grid view)
	val allGenres = fragmentDestination<GenresGridV2Fragment>()

	// All favorites across all libraries
	val allFavorites = fragmentDestination<FavoritesBrowseFragment>()

	// Folder view - browse by folder structure
	val folderView = fragmentDestination<FolderViewFragment>()

	// Browse items by genre (using the V2 library browser)
	fun genreBrowse(
		genreName: String,
		parentId: UUID? = null,
		includeType: String? = null,
		serverId: UUID? = null,
		displayPreferencesId: String? = null,
		parentItemId: UUID? = null,
	) = fragmentDestination<LibraryBrowseFragment>(
		LibraryBrowseFragment.ARG_GENRE_NAME to genreName,
		LibraryBrowseFragment.ARG_PARENT_ID to parentId?.toString(),
		LibraryBrowseFragment.ARG_INCLUDE_TYPE to includeType,
		LibraryBrowseFragment.ARG_SERVER_ID to serverId?.toString(),
		LibraryBrowseFragment.ARG_DISPLAY_PREFS_ID to displayPreferencesId,
		LibraryBrowseFragment.ARG_PARENT_ITEM_ID to parentItemId?.toString(),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun libraryByGenres(item: BaseItemDto, includeType: String) =
		fragmentDestination<GenresGridV2Fragment>(
			Extras.Folder to Json.Default.encodeToString(item),
			Extras.IncludeType to includeType,
		)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun libraryByLetter(item: BaseItemDto, includeType: String) =
		fragmentDestination<ByLetterFragment>(
			Extras.Folder to Json.Default.encodeToString(item),
			Extras.IncludeType to includeType,
		)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun librarySuggestions(item: BaseItemDto) =
		fragmentDestination<SuggestedMoviesFragment>(
			Extras.Folder to Json.Default.encodeToString(item),
		)

	// Item details
	@JvmOverloads
	fun itemDetails(item: UUID, serverId: UUID? = null) = fragmentDestination<ItemDetailsFragment>(
		"ItemId" to item.toString(),
		"ServerId" to serverId?.toString(),
	)

	@JvmOverloads
	fun itemDetailsLegacy(item: UUID, serverId: UUID? = null) = fragmentDestination<FullDetailsFragment>(
		"ItemId" to item.toString(),
		"ServerId" to serverId?.toString(),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun channelDetails(item: UUID, channel: UUID, programInfo: BaseItemDto) =
		fragmentDestination<FullDetailsFragment>(
			"ItemId" to item.toString(),
			"ChannelId" to channel.toString(),
			"ProgramInfo" to Json.Default.encodeToString(programInfo),
		)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun seriesTimerDetails(item: UUID, seriesTimer: SeriesTimerInfoDto) =
		fragmentDestination<FullDetailsFragment>(
			"ItemId" to item.toString(),
			"SeriesTimer" to Json.Default.encodeToString(seriesTimer),
		)

	@JvmOverloads
	fun itemList(item: UUID, serverId: UUID? = null) = fragmentDestination<ItemListFragment>(
		"ItemId" to item.toString(),
		"ServerId" to serverId?.toString(),
	)

	fun musicFavorites(parent: UUID) = fragmentDestination<MusicFavoritesListFragment>(
		"ParentId" to parent.toString(),
	)

	// Trailer player
	fun trailerPlayer(
		videoId: String,
		startSeconds: Double = 0.0,
		segmentsJson: String = "[]",
	) = fragmentDestination<TrailerPlayerFragment>(
		TrailerPlayerFragment.ARG_VIDEO_ID to videoId,
		TrailerPlayerFragment.ARG_START_SECONDS to startSeconds,
		TrailerPlayerFragment.ARG_SEGMENTS_JSON to segmentsJson,
	)

	// Live TV
	val liveTvGuide = fragmentDestination<LiveTvGuideFragment>()
	val liveTvSchedule = fragmentDestination<ScheduleBrowseFragment>()
	val liveTvRecordings = fragmentDestination<RecordingsBrowseFragment>()
	val liveTvSeriesRecordings = fragmentDestination<SeriesRecordingsBrowseFragment>()

	// Playback
	val nowPlaying = fragmentDestination<AudioNowPlayingFragment>()

	fun photoPlayer(
		item: UUID,
		autoPlay: Boolean,
		albumSortBy: ItemSortBy?,
		albumSortOrder: SortOrder?,
	) = fragmentDestination<PhotoPlayerFragment>(
		PhotoPlayerFragment.ARGUMENT_ITEM_ID to item.toString(),
		PhotoPlayerFragment.ARGUMENT_ALBUM_SORT_BY to albumSortBy?.serialName,
		PhotoPlayerFragment.ARGUMENT_ALBUM_SORT_ORDER to albumSortOrder?.serialName,
		PhotoPlayerFragment.ARGUMENT_AUTO_PLAY to autoPlay,
	)

	fun videoPlayer(position: Int?) = fragmentDestination<CustomPlaybackOverlayFragment>(
		"Position" to (position ?: 0)
	)

	fun videoPlayerNew(position: Int?) = fragmentDestination<VideoPlayerFragment>(
		VideoPlayerFragment.EXTRA_POSITION to position
	)

	fun nextUp(item: UUID) = fragmentDestination<NextUpFragment>(
		NextUpFragment.ARGUMENT_ITEM_ID to item.toString()
	)

	fun stillWatching(item: UUID) = fragmentDestination<StillWatchingFragment>(
		NextUpFragment.ARGUMENT_ITEM_ID to item.toString()
	)

	// Jellyseerr
	val jellyseerrDiscover = fragmentDestination<DiscoverFragment>()
	val jellyseerrRequests = fragmentDestination<RequestsFragment>()
	val jellyseerrSettings = fragmentDestination<JellyseerrSettingsFragment>()
	
	fun jellyseerrBrowseBy(
		filterId: Int, 
		filterName: String, 
		mediaType: String,
		filterType: BrowseFilterType = BrowseFilterType.GENRE
	) = fragmentDestination<JellyseerrBrowseByFragment>(
		"filter_id" to filterId,
		"filter_name" to filterName,
		"media_type" to mediaType,
		"filter_type" to filterType.name,
	)
	
	// Convenience methods for specific filter types
	fun jellyseerrBrowseByGenre(genreId: Int, genreName: String, mediaType: String) = 
		jellyseerrBrowseBy(genreId, genreName, mediaType, BrowseFilterType.GENRE)
	
	fun jellyseerrBrowseByNetwork(networkId: Int, networkName: String) = 
		jellyseerrBrowseBy(networkId, networkName, "tv", BrowseFilterType.NETWORK)
	
	fun jellyseerrBrowseByStudio(studioId: Int, studioName: String) = 
		jellyseerrBrowseBy(studioId, studioName, "movie", BrowseFilterType.STUDIO)
	
	fun jellyseerrMediaDetails(itemJson: String) = fragmentDestination<MediaDetailsFragment>(
		"item" to itemJson
	)
	
	fun jellyseerrPersonDetails(personId: Int) = fragmentDestination<PersonDetailsFragment>(
		"personId" to personId.toString()
	)
}
