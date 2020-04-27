package com.kelmer.abn.foursquare.domain.usecase

import com.kelmer.abn.foursquare.common.usecase.ResourceFlowableUseCase
import com.kelmer.abn.foursquare.common.util.SchedulerProvider
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import com.kelmer.abn.foursquare.data.repository.VenueRepository
import io.reactivex.Flowable

open class GetVenueDetailsUseCase(private val venueRepository: VenueRepository, schedulerProvider: SchedulerProvider) :
    ResourceFlowableUseCase<VenueDetails, GetVenueDetailsUseCase.Params>(schedulerProvider) {

    data class Params(
        val id: String
    )

    override fun buildUseCase(params: Params): Flowable<VenueDetails> {
        return venueRepository.getVenue(params.id)
    }

}