package com.app.testshared

import com.app.domain.Comment
import com.app.domain.ImagePlace
import com.app.domain.Place

val sampleImagePlace = ImagePlace(
    0,
    0,
    "",
    0
)
val samplePlace = Place(
    0,
    "title",
    "shortDescription",
    "largeDescription",
    images = listOf(sampleImagePlace),
    "location",
    "latitude",
    "longitude",
    false
)
val sampleComment = Comment(
    "id",
    1,
    0,
    "timeRegister",
    "nameUser",
    "commentText"
)