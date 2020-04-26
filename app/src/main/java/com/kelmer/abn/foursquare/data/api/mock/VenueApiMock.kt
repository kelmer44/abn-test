package com.kelmer.abn.foursquare.data.api.mock

import com.kelmer.abn.foursquare.data.api.VenueApi
import com.kelmer.abn.foursquare.data.api.model.LocationData
import com.kelmer.abn.foursquare.data.api.model.detail.VenueListResponse
import com.kelmer.abn.foursquare.data.api.model.list.ContactInfoData
import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailData
import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailResponse
import com.kelmer.abn.foursquare.data.api.model.photos.PhotoData
import com.kelmer.abn.foursquare.data.api.model.photos.PhotosResponse
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class VenueApiMock : VenueApi {

    private val photos = listOf<PhotoData>(
        PhotoData(
            "1A", "https://fastly.4sqi.net/img/general/",
            "/7398326_J1BeeyQ4UrBekidrZwCTOjgqTdnQZyfx3Pc-zfQPBWw.jpg",
            1920, 1440
        ),
        PhotoData(
            "1B", "https://fastly.4sqi.net/img/general/",
            "/7398326_J1BeeyQ4UrBekidrZwCTOjgqTdnQZyfx3Pc-zfQPBWw.jpg",
            1920, 1440
        ),
        PhotoData(
            "1C", "https://fastly.4sqi.net/img/general/",
            "/7398326_J1BeeyQ4UrBekidrZwCTOjgqTdnQZyfx3Pc-zfQPBWw.jpg",
            1920, 1440
        )
    )


    private val venueMap = hashMapOf<String, VenueDetailData>(
        "1" to VenueDetailData(
            "1", "Venue 1", MockUtils.getLipsum(300),
            LocationData(53.1, 4.89, "Herengracht 10", "Nederland", "Amsterdam", "Nord Holland"),
            ContactInfoData("+31657664847", "+31 657 664 847", "twitteraccount"),
            "http://google.es",
            3.4f,
            PhotoData(
                "234",
                "https://fastly.4sqi.net/img/general/",
                "/7398326_J1BeeyQ4UrBekidrZwCTOjgqTdnQZyfx3Pc-zfQPBWw.jpg",
                1920, 1440
            )
        ),
        "2" to VenueDetailData(
            "2", "Venue 2", MockUtils.getLipsum(500),
            LocationData(53.1, 4.89, "Prinsengracht 40", "Nederland", "Amsterdam", "Nord Holland"),
            ContactInfoData("+31657664847", "+31 657 664 847", "twitteraccount"),
            "http://google.es",
            9f,
            PhotoData(
                "234",
                "https://fastly.4sqi.net/img/general/",
                "/7398326_J1BeeyQ4UrBekidrZwCTOjgqTdnQZyfx3Pc-zfQPBWw.jpg",
                1920, 1440
            )
        ),
        "3" to VenueDetailData(
            "3", "Venue 3", MockUtils.getLipsum(400),
            LocationData(53.1, 4.89, "Keizergracht 65", "Nederland", "Amsterdam", "Nord Holland"),
            ContactInfoData("+31657664847", "+31 657 664 847", "twitteraccount"),
            "http://google.es",
            6.7f,
            PhotoData(
                "234",
                "https://fastly.4sqi.net/img/general/",
                "/7398326_J1BeeyQ4UrBekidrZwCTOjgqTdnQZyfx3Pc-zfQPBWw.jpg",
                1920, 1440
            )
        )
    )

    override fun getVenues(
        query: String,
        location: String,
        radius: Int
    ): Single<VenueListResponse> {
        return MockUtils.delayedSingle(MockUtils.buildVenueListResponse(venueMap.values.toList()))
    }

    override fun getVenue(id: String): Single<VenueDetailResponse> {
        val venue = venueMap[id]
        return if (venue != null)
            MockUtils.delayedSingle(MockUtils.buildVenueDetailResponse(venue))
        else {
            val httpException = HttpException(
                Response.error<VenueDetailResponse>(
                    404,
                    "ResponseBody".toResponseBody("application/json".toMediaType())
                )
            )
            Single.error(httpException)
        }
    }

    override fun getVenuePhotos(id: String): Single<PhotosResponse> {
        return MockUtils.delayedSingle(MockUtils.buildPhotosResponse(photos))
    }
}