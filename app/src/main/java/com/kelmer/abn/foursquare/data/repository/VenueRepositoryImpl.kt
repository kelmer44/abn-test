package com.kelmer.abn.foursquare.data.repository

import com.kelmer.abn.foursquare.data.api.VenueApi
import com.kelmer.abn.foursquare.data.converter.PhotoConverter
import com.kelmer.abn.foursquare.data.converter.VenueConverter
import com.kelmer.abn.foursquare.data.converter.VenueDetailsConverter
import com.kelmer.abn.foursquare.data.db.model.Venue
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import com.kelmer.abn.foursquare.domain.model.LatLon
import io.reactivex.Single

class VenueRepositoryImpl(private val venueApi: VenueApi) : VenueRepository {

    private val venueConverter = VenueConverter()
    private val venueDetailsConverter = VenueDetailsConverter(PhotoConverter())

    override fun getVenues(search: String, location: LatLon): Single<List<Venue>> {
        return venueApi.getVenues(search, location.toQuery()).map { result ->
            result.response.venues.map(venueConverter::convert)
        }
    }

    override fun getVenue(id: String): Single<VenueDetails> {
        return venueApi.getVenue(id).map {
            it.response
        }
//            .map { detail ->
//                venueDetailsConverter.convert(detail.venue, listOf())
//            }
            .flatMap { detail ->
                venueApi.getVenuePhotos(id).map { it.response }.map { photos ->
                    venueDetailsConverter.convert(detail.venue, photos.photos.items)
                }
            }
    }

}