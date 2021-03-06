package com.kelmer.abn.foursquare.common.di

import com.kelmer.abn.foursquare.data.repository.VenueRepository
import com.kelmer.abn.foursquare.data.repository.VenueRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single<VenueRepository> {
        VenueRepositoryImpl(
//            venueApi = get(named(QUALIFIER_API_MOCK)),
                        get(named(QUALIFIER_API_RETROFIT)),
            venueDao = get()

        )
    }
}