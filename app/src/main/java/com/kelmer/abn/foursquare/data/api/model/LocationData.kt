package com.kelmer.abn.foursquare.data.api.model

data class LocationData(
    val lat: Double, val lng: Double,
    val address: String?,
    val country: String?,
    val city: String?,
    val state: String?
)