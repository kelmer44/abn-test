package com.kelmer.abn.foursquare.data.api.model.detail

import com.kelmer.abn.foursquare.data.api.model.LocationData

data class VenueData(
    val id: String,
    val name: String,
    val location : LocationData
)