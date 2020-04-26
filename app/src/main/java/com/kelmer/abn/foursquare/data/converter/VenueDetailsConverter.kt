package com.kelmer.abn.foursquare.data.converter

import com.kelmer.abn.foursquare.data.api.model.LocationData
import com.kelmer.abn.foursquare.data.api.model.detail.VenueData
import com.kelmer.abn.foursquare.data.api.model.list.ContactInfoData
import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailData
import com.kelmer.abn.foursquare.data.api.model.photos.PhotoData
import com.kelmer.abn.foursquare.data.db.model.ContactInfo
import com.kelmer.abn.foursquare.data.db.model.LocationInfo
import com.kelmer.abn.foursquare.data.db.model.VenueDetails

class VenueDetailsConverter(private val photoConverter: PhotoConverter) :
    BiConverter<VenueDetailData, List<PhotoData>, VenueDetails> {
    override fun convert(input1: VenueDetailData, input2: List<PhotoData>): VenueDetails {
        return VenueDetails(
            id = input1.id,
            name = input1.name,
            photos = photoConverter.convert(input2),
            bestPhoto = if (input1.bestPhoto != null) photoConverter.convert(input1.bestPhoto) else null,
            rating = input1.rating ?: 0.0f,
            hasRating = input1.rating != null,
            description = input1.description?:"",
            contactInfo = mapContactData(input1.contact),
            locationInfo = mapLocationData(input1.location),
            formattedAddress = resolveLocation(input1)
            )
    }

    private fun mapContactData(input: ContactInfoData): ContactInfo {
        return ContactInfo(input.twitter ?: "", input.formattedPhone ?: "")
    }

    private fun mapLocationData(input: LocationData): LocationInfo {
        return LocationInfo(input.address?:"", input.city ?: "", input.country ?: "")
    }

    private fun resolveLocation(input: VenueDetailData): String {
        return if(!input.location.address.isNullOrBlank()){
            "${input.location.address}, ${input.location.city}"
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