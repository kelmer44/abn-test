package com.kelmer.abn.foursquare.common.di

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("client_id", "AGNZQ4BQSBXQMT0ZB20E1FOGOMCFOM2XZRUPQKDSBOM1Z1BI")
            .addQueryParameter("client_secret", "PPJ2IPSPUVNKSWML52NEYRQL0T5VYKDAGSYOUWRMKWDDM5RA")
            .addQueryParameter("v", "20200420")
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}