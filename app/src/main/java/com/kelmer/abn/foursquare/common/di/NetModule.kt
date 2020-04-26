package com.kelmer.abn.foursquare.common.di

import com.kelmer.abn.foursquare.R
import com.kelmer.abn.foursquare.common.di.provider.ApiServiceProvider
import com.kelmer.abn.foursquare.common.di.provider.HttpClientProvider
import com.kelmer.abn.foursquare.data.api.VenueApi
import com.kelmer.abn.foursquare.data.api.mock.VenueApiMock
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module


const val QUALIFIER_CLIENT_ID = "client_id"
const val QUALIFIER_CLIENT_SECRET = "client_secret"
const val QUALIFIER_API_MOCK = "mock"
const val QUALIFIER_API_RETROFIT = "retrofit"

val netModule = module {

    single<String>(named(QUALIFIER_CLIENT_ID)) { androidContext().getString(R.string.client_id) }
    single<String>(named(QUALIFIER_CLIENT_SECRET)) { androidContext().getString(R.string.client_secret) }

    single {
        AuthInterceptor(
            clientId = get(named(QUALIFIER_CLIENT_ID)),
            clientSecret = get(named(QUALIFIER_CLIENT_SECRET))
        )
    }
    single { HttpClientProvider.createHttpClient(get()).build() }
    single { ApiServiceProvider.createRetrofit(httpClient = get(), moshi = get()) }
    single<VenueApi>(named(QUALIFIER_API_RETROFIT)) { ApiServiceProvider.getService(get()) }
    single<VenueApi>(named(QUALIFIER_API_MOCK)) { VenueApiMock() }
}