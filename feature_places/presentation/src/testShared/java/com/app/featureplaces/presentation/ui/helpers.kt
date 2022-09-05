package com.app.featureplaces.presentation.ui

import com.app.featureplaces.data.PlacesRepository
import com.app.featureplaces.data.RegionRepository
import com.app.featureplaces.presentation.*
import com.app.featureplaces.presentation.data.database.ImagePlace
import com.app.featureplaces.presentation.data.database.PlaceRoomDataSource
import com.app.featureplaces.presentation.data.database.PlaceWithImagePlaces
import com.app.featureplaces.presentation.data.server.PlaceServerDataSource
import com.app.featureplaces.presentation.data.server.RemotePlace
/*
fun buildRepositoryWith(
    localData: List<PlaceWithImagePlaces>,
    localDataImages: List<ImagePlace>,
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
    PlaceWithImagePlaces(

    )
}

fun buildDatabaseImages(vararg id:Int)=id.map {
    ImagePlace(
        1,
        1,
        ""
    )
}

fun buildRemoteMovies(vararg id: Int) = id.map {
    RemotePlace(

    )
}
*/