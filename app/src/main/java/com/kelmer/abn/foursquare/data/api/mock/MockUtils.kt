package com.kelmer.abn.foursquare.data.api.mock

import com.kelmer.abn.foursquare.data.api.model.LocationData
import com.kelmer.abn.foursquare.data.api.model.MetaData
import com.kelmer.abn.foursquare.data.api.model.detail.ListResponseData
import com.kelmer.abn.foursquare.data.api.model.detail.VenueData
import com.kelmer.abn.foursquare.data.api.model.detail.VenueListResponse
import com.kelmer.abn.foursquare.data.api.model.list.ContactInfoData
import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailData
import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailResponse
import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailResponseData
import com.kelmer.abn.foursquare.data.api.model.photos.PhotoData
import com.kelmer.abn.foursquare.data.api.model.photos.PhotosData
import com.kelmer.abn.foursquare.data.api.model.photos.PhotosResponse
import com.kelmer.abn.foursquare.data.api.model.photos.PhotosResponseData
import io.reactivex.Single
import java.util.concurrent.TimeUnit

object MockUtils {

    val mockedVenues = listOf(
        VenueDetailData(
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
        VenueDetailData(
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
        VenueDetailData(
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
    val mockedPhotos = listOf<PhotoData>(
        PhotoData(
            "1A", "https://fastly.4sqi.net/img/general/",
            "/331946_4cWTOeJejQ-pAl3pvllPed8lnax-FAV6X8VK9UIEfWQ.jpg",
            959, 717
        ),
        PhotoData(
            "1B", "https://fastly.4sqi.net/img/general/",
            "/7398326_J1BeeyQ4UrBekidrZwCTOjgqTdnQZyfx3Pc-zfQPBWw.jpg",
            1920, 1440
        ),
        PhotoData(
            "1C", "https://fastly.4sqi.net/img/general/",
            "/12599336_c1HXD02egzZJcdogt2P2USFt71khARwzH-wmXAjtCuA.jpg",
            612, 612
        )
    )
    fun fromVenueDetailsToVenue(venue: VenueDetailData): VenueData {
        return VenueData(
            venue.id,
            venue.name,
            venue.location
        )
    }

    fun buildVenueListResponse(venues: List<VenueDetailData>): VenueListResponse {
        return VenueListResponse(
            MetaData(200, "212314"),
            ListResponseData(
                venues.map { fromVenueDetailsToVenue(it) }
            )
        )
    }

    fun buildVenueDetailResponse(venue: VenueDetailData): VenueDetailResponse {
        return VenueDetailResponse(
            MetaData(200, "34324234"),
            VenueDetailResponseData(
                venue
            )
        )
    }

    fun buildPhotosResponse(photos: List<PhotoData>): PhotosResponse {
        return PhotosResponse(
            MetaData(200, "2342423"),
            PhotosResponseData(
                photos = PhotosData(
                    photos.size,
                    photos
                )
            )
        )
    }

    /**
     * mocking network response with some delay
     */
    fun <T> delayedSingle(content: T): Single<T> = Single.just(content).delay(1000L, TimeUnit.MILLISECONDS)

    private const val lipsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum tempor tortor in elit condimentum maximus. Aliquam aliquet, sem placeId rhoncus aliquet, quam mauris vulputate ante, sit amet gravida felis ipsum at enim. Sed fermentum aliquam leo eu maximus. Praesent consequat sagittis lacinia."
    fun getLipsum(max: Int = 9999999): String {
        return lipsum.substring(0, lipsum.length.coerceAtMost(max))
    }

    fun getRandomLipsum(): String {
        return lipsum.split(" ").shuffled().first()
    }


}