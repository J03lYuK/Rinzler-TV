package uk.rinzler.tv.di

import uk.rinzler.tv.data.service.pluginsync.PluginSyncService
import uk.rinzler.tv.preference.LiveTvPreferences
import uk.rinzler.tv.preference.PreferencesRepository
import uk.rinzler.tv.preference.SystemPreferences
import uk.rinzler.tv.preference.TelemetryPreferences
import uk.rinzler.tv.preference.UserPreferences
import uk.rinzler.tv.preference.UserSettingPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferenceModule = module {
	single { PluginSyncService(androidContext(), get(), get(), get(), get(), get(), get()) }
	single { PreferencesRepository(get(), get(), get(), get()) }

	single { LiveTvPreferences(get()) }
	single { UserSettingPreferences(get()) }
	single { UserPreferences(get()) }
	single { SystemPreferences(get()) }
	single { TelemetryPreferences(get()) }
}
