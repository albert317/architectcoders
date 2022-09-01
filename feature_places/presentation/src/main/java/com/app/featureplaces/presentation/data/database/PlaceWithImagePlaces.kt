package com.app.featureplaces.presentation.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class PlaceWithImagePlaces(
    @Embedded val place: Place,
    @Relation(
        parentColumn = "id",
        entityColumn = "idPlace"
    )
    val listOfImages: List<ImagePlace>
)