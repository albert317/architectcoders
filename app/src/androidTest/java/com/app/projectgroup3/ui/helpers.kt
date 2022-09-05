package com.app.projectgroup3.ui

import com.app.domain.Comment
import com.app.domain.ImagePlace
import com.app.domain.Place
import com.app.featureplaces.data.PlacesRepository
import com.app.featureplaces.data.RegionRepository
import com.app.featureplaces.presentation.data.database.PlaceRoomDataSource
import com.app.featureplaces.presentation.data.database.PlaceWithImagePlaces
import com.app.featureplaces.presentation.data.server.PlaceServerDataSource
import com.app.featureplaces.presentation.data.server.RemotePlace
import com.app.featureplaces.presentation.ui.*
import com.app.featureplaces.presentation.data.database.ImagePlace as ImagePlaceData

fun buildRepositoryWith(
    localData: List<PlaceWithImagePlaces>,
    localDataImages: List<ImagePlaceData>,
    remoteData: List<RemotePlace>,
): PlacesRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    val localDataSource = PlaceRoomDataSource(
        FakePlaceDao(localData),
        FakeImagePlaceDao(localDataImages)
    )
    val remoteDataSource = PlaceServerDataSource("1234", FakePlaceRemoteService(remoteData))
    return PlacesRepository(localDataSource, remoteDataSource, regionRepository)
}

fun buildDatabaseMovies(vararg id: Int) = id.map {

    com.app.featureplaces.presentation.data.database.Place(
        id = 0,
        name = "title",
        shortDescription = "shortDescription",
        largeDescription = "largeDescription",
        location = "location",
        latitude = "latitude",
        longitude = "longitude",
        favorite = false
    )
    /*
    PlaceWithImagePlaces(
        com.app.featureplaces.presentation.data.database.Place(
            id = 0,
            name = "title",
            shortDescription = "shortDescription",
            largeDescription = "largeDescription",
            location = "location",
            latitude = "latitude",
            longitude = "longitude",
            favorite = false
        ),
        listOfImages = listOf(
            com.app.featureplaces.presentation.data.database.ImagePlace(
                id = 0,
                idPlace = 1,
                "",
                0
            )
        )
    )*/
}

fun buildDatabaseImages(vararg id: Int) = id.map {
    RemotePlace(
        id = 0,
        name = "title",
        shortDescription = "shortDescription",
        largeDescription = "largeDescription",
        location = "location",
        latitude = "latitude",
        longitude = "longitude",
        images = listOf()
    )
}

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