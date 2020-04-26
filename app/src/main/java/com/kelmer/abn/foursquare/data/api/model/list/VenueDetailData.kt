package com.kelmer.abn.foursquare.data.api.model.list

import com.kelmer.abn.foursquare.data.api.model.LocationData
import com.kelmer.abn.foursquare.data.api.model.photos.PhotoData

data class VenueDetailData(
    val id: String,
    val name: String,
    val description: String?,
    val location: LocationData,
    val contact: ContactInfoData,
    val url: String?,
    val rating: Float?,
    val bestPhoto: PhotoData?
)


data class ContactInfoData(
    val phone: String?,
    val formattedPhone: String?,
    val twitter: String?
)