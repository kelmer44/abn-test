package com.kelmer.abn.foursquare.data.api.mock

import com.kelmer.abn.foursquare.common.util.NetworkInteractor
import com.kelmer.abn.foursquare.data.api.VenueApi
import com.kelmer.abn.foursquare.data.api.model.detail.VenueListResponse
import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailData
import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailResponse
import com.kelmer.abn.foursquare.data.api.model.photos.PhotosResponse
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

/**
 * we inject the network interactor to mock the behavior of not having a connection
 */
class VenueApiMock(val networkInteractor: NetworkInteractor) : VenueApi {

    private val photos = MockUtils.mockedPhotos

    private val venueMap: Map<String, VenueDetailData> = MockUtils.mockedVenues.map {
        it.id to it
    }.toMap()

    override fun getVenues(
        query: String,
        location: String,
        radius: Int
    ): Single<VenueListResponse> {
        return MockUtils.delayedSingle(MockUtils.buildVenueListResponse(venueMap.values.toList()))
    }

    override fun getVenue(id: String): Single<VenueDetailResponse> {
        val venue = venueMap[id]
        return if(!networkInteractor.hasNetworkConnection()){
            Single.error(UnknownHostException())
        }
        else if (venue != null)
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