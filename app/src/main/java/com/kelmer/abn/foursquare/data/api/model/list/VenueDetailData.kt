package com.kelmer.abn.foursquare.data.api.model.list

import com.kelmer.abn.foursquare.data.api.model.LocationData

data class VenueDetailData(
    val id: String,
    val name: String,
    val location : LocationData)