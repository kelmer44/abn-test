package com.kelmer.abn.foursquare.data.api.model.photos

import com.kelmer.abn.foursquare.data.api.model.MetaData

data class PhotosResponse(val meta: MetaData,
                          val response: PhotosResponseData) {
}