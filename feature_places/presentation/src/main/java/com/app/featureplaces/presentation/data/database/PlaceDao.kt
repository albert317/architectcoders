package com.app.featureplaces.presentation.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("SELECT COUNT(id) FROM Place")
    suspend fun placeCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(places: List<Place>)

    @Transaction
    @Query("SELECT * FROM Place where id=:idPlace")
    fun findById(idPlace: Int): Flow<PlaceWithImagePlaces>

    @Transaction
    @Query("SELECT * FROM Place")
    fun getAll(): Flow<List<PlaceWithImagePlaces>>

}
