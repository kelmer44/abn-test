package com.kelmer.abn.foursquare.data.repository

import android.util.Log
import com.kelmer.abn.foursquare.common.util.NetworkInteractor
import com.kelmer.abn.foursquare.data.api.VenueApi
import com.kelmer.abn.foursquare.data.converter.PhotoConverter
import com.kelmer.abn.foursquare.data.converter.VenueConverter
import com.kelmer.abn.foursquare.data.converter.VenueDetailsConverter
import com.kelmer.abn.foursquare.data.db.dao.VenueDao
import com.kelmer.abn.foursquare.data.db.model.Venue
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import com.kelmer.abn.foursquare.domain.model.LatLon
import io.reactivex.Flowable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class VenueRepositoryImpl(
    private val venueApi: VenueApi,
    private val venueDao: VenueDao
) : VenueRepository, KoinComponent {

    private val venueConverter = VenueConverter()
    private val venueDetailsConverter = VenueDetailsConverter(PhotoConverter())

    override fun getVenues(search: String, location: LatLon): Single<List<Venue>> {
        return venueApi.getVenues(search, location.toQuery())
            .map { result ->
                result.response.venues.map(venueConverter::convert)
            }
    }

    override fun getVenue(id: String): Flowable<VenueDetails> {
        val remoteVenue =
            venueApi.getVenue(id)
                .flatMap { detail ->
                    venueApi.getVenuePhotos(id).map { it.response }.map { photos ->
                        venueDetailsConverter.convert(detail.response.venue, photos.photos.items)
                    }
                }
                .doOnError {
                     Log.e("NO NETWORK", "Error!: ${it}!")
                }
                .doOnSuccess {
                    venueDao.saveVenue(it)
                }
        val localVenue = venueDao.getVenue(id)
        return Single.concat(
            localVenue.doOnSuccess {
                Log.i("NO NETWORK", "From local $it")
            },
            remoteVenue.doOnSuccess {
                Log.i("NO NETWORK", "From remote $it")
            }
        )
    }

}