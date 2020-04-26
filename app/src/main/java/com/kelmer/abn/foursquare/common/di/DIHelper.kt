package com.kelmer.abn.foursquare.common.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

object DIHelper {

    fun init(context: Context) =
        startKoin {
            androidLogger()
            androidContext(context)
            modules(
                listOf(
                    appModule,
                    netModule,
                    useCaseModule,
                    repositoryModule,
                    dbModule
                )
            )
        }
}