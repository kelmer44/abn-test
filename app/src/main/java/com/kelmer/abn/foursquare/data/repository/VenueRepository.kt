package com.kelmer.abn.foursquare.data.repository

import com.kelmer.abn.foursquare.data.db.model.Venue
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import com.kelmer.abn.foursquare.domain.model.LatLon
import io.reactivex.Single

interface VenueRepository {

    fun getVenues(search: String, location: LatLon) : Single<List<Venue>>

    fun getVenue(id: String) : Single<VenueDetails>

}