package com.kelmer.abn.foursquare.domain.usecase

import com.kelmer.abn.foursquare.common.usecase.ResourceSingleUseCase
import com.kelmer.abn.foursquare.common.util.SchedulerProvider
import com.kelmer.abn.foursquare.data.db.model.Venue
import com.kelmer.abn.foursquare.data.repository.VenueRepository
import com.kelmer.abn.foursquare.domain.model.LatLon
import io.reactivex.Single

class GetVenuesUseCase(private val venueRepository: VenueRepository, schedulerProvider: SchedulerProvider) :
    ResourceSingleUseCase<List<Venue>, GetVenuesUseCase.Params>(schedulerProvider) {

    data class Params(
        val query: String,
        val location: LatLon
    )

    override fun buildUseCase(params: Params): Single<List<Venue>> {
        return venueRepository.getVenues(params.query, params.location)
    }
}