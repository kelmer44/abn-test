package com.kelmer.abn.foursquare.data.api.model.detail

import com.kelmer.abn.foursquare.data.api.model.MetaData

data class VenueListResponse(
    val meta: MetaData,
    val response: ListResponseData
)