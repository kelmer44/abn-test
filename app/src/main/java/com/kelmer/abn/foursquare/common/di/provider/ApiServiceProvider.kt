package com.kelmer.abn.foursquare.common.di.provider

import com.kelmer.abn.foursquare.data.api.mock.VenueApiMock
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val FOURSQUARE_BASE_URL: String = "https://api.foursquare.com/v2/"

object ApiServiceProvider {

    inline fun <reified T> getService(retrofit: Retrofit): T {
        return retrofit.create(T::class.java)
    }

    fun createRetrofit(httpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(FOURSQUARE_BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}