package com.kelmer.abn.foursquare.domain.usecase

import com.kelmer.abn.foursquare.common.usecase.ResourceFlowableUseCase
import com.kelmer.abn.foursquare.common.usecase.ResourceSingleUseCase
import com.kelmer.abn.foursquare.common.util.SchedulerProvider
import com.kelmer.abn.foursquare.data.db.model.Venue
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import com.kelmer.abn.foursquare.data.repository.VenueRepository
import com.kelmer.abn.foursquare.domain.model.LatLon
import io.reactivex.Flowable
import io.reactivex.Single
import org.intellij.lang.annotations.Flow

class GetVenueUseCase(private val venueRepository: VenueRepository, schedulerProvider: SchedulerProvider) :
    ResourceFlowableUseCase<VenueDetails, GetVenueUseCase.Params>(schedulerProvider) {

    data class Params(
        val id: String
    )

    override fun buildUseCase(params: Params): Flowable<VenueDetails> {
        return venueRepository.getVenue(params.id)
    }

}