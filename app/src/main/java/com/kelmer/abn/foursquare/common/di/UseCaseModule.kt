package com.kelmer.abn.foursquare.common.di

import com.kelmer.abn.foursquare.domain.usecase.GetVenueDetailsUseCase
import com.kelmer.abn.foursquare.domain.usecase.GetVenuesUseCase
import com.kelmer.abn.foursquare.domain.usecase.IGetVenueDetailsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetVenuesUseCase(
            venueRepository = get(),
            schedulerProvider = get(),
            networkInteractor = get()
        )
    }

    factory<IGetVenueDetailsUseCase> {
        GetVenueDetailsUseCase(
            venueRepository = get(),
            schedulerProvider = get(),
            networkInteractor = get()
        )
    }

}