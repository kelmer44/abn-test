package com.kelmer.abn.foursquare.common.di.provider

import android.content.Context
import androidx.room.Room
import com.kelmer.abn.foursquare.BuildConfig
import com.kelmer.abn.foursquare.data.db.config.AppDatabase

object DbProvider {
    fun provideAppDatabase(context: Context): AppDatabase {
        val isDebug = if (BuildConfig.DEBUG) ".debug" else ""
        val suffix = "$isDebug"

        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java, "abn-foursquare-db$suffix"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

}