package com.kelmer.abn.foursquare.data.db.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kelmer.abn.foursquare.data.db.dao.VenueDao
import com.kelmer.abn.foursquare.data.db.model.Venue
import com.kelmer.abn.foursquare.data.db.model.VenueDetails

@Database(
    entities = [
        VenueDetails::class
    ],
    version = 2
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun venueDao() : VenueDao
}