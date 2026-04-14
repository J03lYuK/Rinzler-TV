package uk.rinzler.tv.di

import uk.rinzler.tv.util.ImageHelper
import org.koin.dsl.module

val utilsModule = module {
	single { ImageHelper(get()) }
}
