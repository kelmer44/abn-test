package com.kelmer.abn.foursquare.data.db.model

class VenueDetails(
    val id: String,
    val name: String,
    val photos: List<Photo>,
    val bestPhoto: Photo?,
    val description: String,
    val rating: Float,
    val hasRating: Boolean,
    val contactInfo: ContactInfo,
    val locationInfo: LocationInfo,
    val formattedAddress: String
)