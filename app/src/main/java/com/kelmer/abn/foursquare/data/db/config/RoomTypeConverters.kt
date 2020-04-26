package com.kelmer.abn.foursquare.data.db.config

import android.media.Image
import androidx.room.TypeConverter
import com.kelmer.abn.foursquare.data.db.model.Photo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.*

class RoomTypeConverters() {


    private var moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    @TypeConverter
    fun toImageList(value: String): List<Photo> {
        val type = Types.newParameterizedType(List::class.java, Photo::class.java)
        return moshi.adapter<List<Photo>>(type).fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun fromImageList(list: List<Photo>): String {
        val type = Types.newParameterizedType(List::class.java, Photo::class.java)
        val adapter: JsonAdapter<List<Photo>> = moshi.adapter(type)
        return adapter.toJson(list)
    }

}