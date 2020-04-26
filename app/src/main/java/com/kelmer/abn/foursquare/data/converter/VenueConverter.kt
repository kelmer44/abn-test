package com.kelmer.abn.foursquare.data.converter

import com.kelmer.abn.foursquare.data.api.model.detail.VenueData
import com.kelmer.abn.foursquare.data.db.model.Venue

class VenueConverter : Converter<VenueData, Venue> {
    override fun convert(input: VenueData): Venue {
        return Venue(
            input.id,
            input.name,
            location = LocationConverter.resolveLocation(input.location)
        )
    }

}