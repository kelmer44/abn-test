package com.kelmer.abn.foursquare.common.di

import com.kelmer.abn.foursquare.common.di.provider.ApiServiceProvider
import com.kelmer.abn.foursquare.common.di.provider.HttpClientProvider
import com.kelmer.abn.foursquare.data.api.VenueApi
import com.kelmer.abn.foursquare.data.api.mock.VenueApiMock
import org.koin.core.qualifier.named
import org.koin.dsl.module

val netModule = module {

    single { AuthInterceptor() }
    single { HttpClientProvider.createHttpClient(get()).build() }
    single { ApiServiceProvider.createRetrofit(httpClient = get(), moshi = get()) }
    single<VenueApi> (named("retrofit")){ ApiServiceProvider.getService(get()) }
    single<VenueApi> (named("mock")){ VenueApiMock() }
}