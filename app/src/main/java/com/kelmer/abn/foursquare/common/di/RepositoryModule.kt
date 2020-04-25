package com.kelmer.abn.foursquare.common.di

import com.kelmer.abn.foursquare.data.repository.VenueRepository
import com.kelmer.abn.foursquare.data.repository.VenueRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module{

    single<VenueRepository>{
            VenueRepositoryImpl(
                get()
            )
    }
}