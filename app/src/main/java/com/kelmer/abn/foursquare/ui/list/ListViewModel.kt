package com.kelmer.abn.foursquare.ui.list

import androidx.lifecycle.MutableLiveData
import com.kelmer.abn.foursquare.common.resource.Resource
import com.kelmer.abn.foursquare.common.viewmodel.UseCaseViewModel
import com.kelmer.abn.foursquare.data.db.model.Venue
import com.kelmer.abn.foursquare.domain.model.LatLon
import com.kelmer.abn.foursquare.domain.usecase.GetVenuesUseCase

class ListViewModel(private val getVenuesUseCase: GetVenuesUseCase) :
    UseCaseViewModel(getVenuesUseCase) {

    private val _venues = MutableLiveData<Resource<List<Venue>>>()
    fun getVenues() = _venues

    fun doSearch(query: String, location: LatLon) {
        getVenuesUseCase.execute(
            GetVenuesUseCase.Params(query, location),
            onNext = {
                _venues.value = it
            })
    }
}