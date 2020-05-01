package com.kelmer.abn.foursquare.ui.detail

import androidx.lifecycle.MutableLiveData
import com.kelmer.abn.foursquare.common.resource.Resource
import com.kelmer.abn.foursquare.common.viewmodel.UseCaseViewModel
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import com.kelmer.abn.foursquare.domain.usecase.GetVenueDetailsUseCase
import com.kelmer.abn.foursquare.domain.usecase.IGetVenueDetailsUseCase

class DetailViewModel(private val getVenueDetailsUseCase: IGetVenueDetailsUseCase) :
    UseCaseViewModel(getVenueDetailsUseCase) {


    private val _venues = MutableLiveData<Resource<VenueDetails>>()
    fun getVenue(id: String): MutableLiveData<Resource<VenueDetails>> {
        getVenueDetailsUseCase.execute(
            GetVenueDetailsUseCase.Params(
                id
            ),
            onNext = {
                _venues.value = it
            }
        )
        return _venues
    }
}