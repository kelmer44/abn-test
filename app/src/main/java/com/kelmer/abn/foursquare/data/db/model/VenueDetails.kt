package com.kelmer.abn.foursquare.data.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venues")
class VenueDetails(
    @PrimaryKey
    val id: String,
    val name: String,
    val photos: List<Photo>,
    @Embedded
    val bestPhoto: Photo?,
    val description: String,
    val rating: Float,
    val hasRating: Boolean,
    @Embedded
    val contactInfo: ContactInfo,
    @Embedded
    val locationInfo: LocationInfo,
    val formattedAddress: String
)