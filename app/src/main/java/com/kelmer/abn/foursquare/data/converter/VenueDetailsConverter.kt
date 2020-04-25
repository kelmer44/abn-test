package com.kelmer.abn.foursquare.data.converter

import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailData
import com.kelmer.abn.foursquare.data.api.model.photos.PhotoData
import com.kelmer.abn.foursquare.data.db.model.VenueDetails

class VenueDetailsConverter(private val photoConverter: PhotoConverter) : BiConverter<VenueDetailData, List<PhotoData>, VenueDetails> {
    override fun convert(input1: VenueDetailData, input2: List<PhotoData>): VenueDetails {
        return VenueDetails(input1.id,
        input1.name,
        photoConverter.convert(input2))
    }
}