package com.kelmer.abn.foursquare.data.api.model.list

import com.kelmer.abn.foursquare.data.api.model.MetaData

data class VenueDetailResponse(
    val meta: MetaData,
    val response: VenueDetailResponseData
) {
}