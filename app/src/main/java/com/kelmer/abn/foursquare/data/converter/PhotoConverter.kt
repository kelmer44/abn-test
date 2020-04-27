package com.kelmer.abn.foursquare.data.converter

import com.kelmer.abn.foursquare.data.api.model.photos.PhotoData
import com.kelmer.abn.foursquare.data.db.model.Photo

class PhotoConverter : Converter<PhotoData, Photo> {
    override fun convert(input: PhotoData): Photo {
        return Photo(input.id, "${input.prefix}${input.width}x${input.height}${input.suffix}")
    }

}