package com.kelmer.abn.foursquare.ui.detail

import androidx.lifecycle.MutableLiveData
import com.kelmer.abn.foursquare.common.resource.Resource
import com.kelmer.abn.foursquare.common.viewmodel.UseCaseViewModel
import com.kelmer.abn.foursquare.data.db.model.Venue
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import com.kelmer.abn.foursquare.domain.usecase.GetVenueUseCase

class DetailViewModel(private val getVenueUseCase: GetVenueUseCase) :
    UseCaseViewModel(getVenueUseCase) {


    private val _venues = MutableLiveData<Resource<VenueDetails>>()
    fun getVenue(id: String): MutableLiveData<Resource<VenueDetails>> {
        getVenueUseCase.execute(
            GetVenueUseCase.Params(
                id
            ),
            onNext = {
                _venues.value = it
            }
        )
        return _venues
    }
}