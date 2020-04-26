package com.kelmer.abn.foursquare.data.converter

import com.kelmer.abn.foursquare.data.api.model.detail.VenueData
import com.kelmer.abn.foursquare.data.db.model.Venue

class VenueConverter : Converter<VenueData, Venue> {
    override fun convert(input: VenueData): Venue {
        return Venue(
            input.id,
            input.name,
            location = resolveLocation(input)
        )
    }
    private fun resolveLocation(input: VenueData): String {
        return if(!input.location.address.isNullOrBlank()){
            input.location.address
        } else if(!input.location.city.isNullOrBlank()){
            input.location.city
        } else if(!input.location.state.isNullOrBlank()){
            input.location.state
        } else if(!input.location.country.isNullOrBlank()) {
            input.location.country
        } else {
            ""
        }
    }
}