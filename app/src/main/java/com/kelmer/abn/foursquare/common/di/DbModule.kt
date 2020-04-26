package com.kelmer.abn.foursquare.common.di

import com.kelmer.abn.foursquare.common.di.provider.DbProvider
import com.kelmer.abn.foursquare.data.db.config.AppDatabase
import org.koin.dsl.module

val dbModule = module {
    single { DbProvider.provideAppDatabase(get()) }

    single {
        get<AppDatabase>().venueDao()
    }
}