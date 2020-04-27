
package com.kelmer.abn.foursquare.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface VenueDao {
    @Query("select * from venues where id = :id")
    fun getVenue(id: String): Single<VenueDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveVenue(it: VenueDetails)
}