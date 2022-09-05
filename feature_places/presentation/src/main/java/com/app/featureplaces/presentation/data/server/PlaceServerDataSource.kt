package com.app.featureplaces.presentation.data.server

import arrow.core.Either
import com.app.featureplaces.data.datasource.PlaceRemoteDataSource
import com.app.domain.Error
import com.app.domain.ImagePlace
import com.app.domain.Place
import com.app.featureplaces.presentation.data.tryCall
import com.app.featureplaces.presentation.di.ApiKey
import javax.inject.Inject

class PlaceServerDataSource @Inject constructor(private val remoteService: RemoteService) :
    PlaceRemoteDataSource {
    override suspend fun findPopularPlaces(region:String): Either<Error, List<Place>> = tryCall {
        remoteService
            .listPopularMovies(region)
            .results.toDomainModel()
    }
}

private fun List<RemotePlace>.toDomainModel(): List<Place> = map { it.toDomainModel() }

private fun RemotePlace.toDomainModel() = Place(
    id,
    name,
    shortDescription,
    largeDescription,
    images.toDomainModel(),
    location,
    latitude,
    longitude,
    favorite = false
)

@JvmName("toDomainModelRemoteImagePlace")
private fun List<RemoteImagePlace>.toDomainModel(): List<ImagePlace> = map { it.toDomainModel() }

private fun RemoteImagePlace.toDomainModel() = ImagePlace(
    id,
    idPlace,
    url,
    position
)