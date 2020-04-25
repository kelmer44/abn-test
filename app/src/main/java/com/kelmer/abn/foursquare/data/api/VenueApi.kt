package com.kelmer.abn.foursquare.data.api

import com.kelmer.abn.foursquare.data.api.model.detail.VenueListResponse
import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailResponse
import com.kelmer.abn.foursquare.data.api.model.photos.PhotosResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VenueApi {

    @GET("venues/search")
    fun getVenues(@Query("query") query: String, @Query("ll") location: String, @Query("radius") radius: Int = 1000) : Single<VenueListResponse>

    @GET("venues/{id}")
    fun getVenue(@Path("id") id: String) : Single<VenueDetailResponse>

    @GET("venues/{id}/photos")
    fun getVenuePhotos(@Path("id") id: String) : Single<PhotosResponse>
}