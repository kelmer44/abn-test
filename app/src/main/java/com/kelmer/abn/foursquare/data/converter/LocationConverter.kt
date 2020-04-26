package com.kelmer.abn.foursquare.data.converter

import com.kelmer.abn.foursquare.data.api.model.LocationData
import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailData

object LocationConverter {

    fun resolveLocation(input: LocationData): String {
        return if(!input.address.isNullOrBlank()){
            "${input.address}, ${input.city}"
        } else if(!input.city.isNullOrBlank()){
            input.city
        } else if(!input.state.isNullOrBlank()){
            input.state
        } else if(!input.country.isNullOrBlank()) {
            input.country
        } else {
            ""
        }
    }

}