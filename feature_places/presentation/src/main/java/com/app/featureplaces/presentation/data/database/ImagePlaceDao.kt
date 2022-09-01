package com.app.featureplaces.presentation.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ImagePlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageOfPlaces(imagesPlaces: List<ImagePlace>)
}