package com.kelmer.abn.foursquare.common.di

import com.kelmer.abn.foursquare.domain.usecase.GetVenueUseCase
import com.kelmer.abn.foursquare.domain.usecase.GetVenuesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetVenuesUseCase(
            venueRepository = get(),
            schedulerProvider = get()
        )
    }

    factory {
        GetVenueUseCase(
            venueRepository = get(),
            schedulerProvider = get()
        )
    }

}