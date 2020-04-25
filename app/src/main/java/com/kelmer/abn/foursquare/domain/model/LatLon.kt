package com.kelmer.abn.foursquare.domain.model

data class LatLon(val lat: Double, val lon: Double){


    fun toQuery() = "$lat,$lon"
}