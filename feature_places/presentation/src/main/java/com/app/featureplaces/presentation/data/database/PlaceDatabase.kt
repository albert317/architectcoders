package com.app.featureplaces.presentation.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Place::class, ImagePlace::class], version = 1, exportSchema = false)
abstract class PlaceDataBase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun imagePlaceDao(): ImagePlaceDao
}