package com.kelmer.abn.foursquare.domain.usecase

import com.kelmer.abn.foursquare.common.usecase.IResourceFlowableUseCase
import com.kelmer.abn.foursquare.data.db.model.VenueDetails

interface IGetVenueDetailsUseCase : IResourceFlowableUseCase<VenueDetails, GetVenueDetailsUseCase.Params> {
}