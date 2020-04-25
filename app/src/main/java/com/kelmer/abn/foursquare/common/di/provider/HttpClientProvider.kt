package com.kelmer.abn.foursquare.common.di.provider

import com.kelmer.abn.foursquare.BuildConfig
import com.kelmer.abn.foursquare.common.di.AuthInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 60L
private const val READ_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 30L

object HttpClientProvider {


    fun createHttpClient(authInterceptor: AuthInterceptor): OkHttpClient.Builder {
        val builder =
            OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }

        builder.addInterceptor(authInterceptor)

        return builder
    }
}