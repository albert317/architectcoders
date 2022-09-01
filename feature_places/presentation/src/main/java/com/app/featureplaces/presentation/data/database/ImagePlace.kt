package com.app.featureplaces.presentation.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ImagePlace(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val idPlace: Int,
    val url: String,
    val position: Int
)