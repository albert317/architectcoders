package com.app.featureplaces.data

import com.app.domain.Error
import com.app.domain.Place
import com.app.featureplaces.data.datasource.PlaceLocalDataSource
import com.app.featureplaces.data.datasource.PlaceRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val localDataSource: PlaceLocalDataSource,
    private val remoteDataSource: PlaceRemoteDataSource,
    private val regionRepository: RegionRepository
) {
    fun getPopularPlaces() = localDataSource.places

    fun findById(id: Int): Flow<Place> = localDataSource.findById(id)

    suspend fun requestPopularMovies(): Error? {
        val region = regionRepository.findLastRegion()
        if (localDataSource.isEmpty()) {
            val places = remoteDataSource.findPopularPlaces(region)
            places.fold(ifLeft = { return it }) { localDataSource.save(it) }
        }
        return null
    }

    suspend fun switchFavorite(place: Place): Error? {
        val updatePlace = place.copy(favorite = !place.favorite)
        return localDataSource.save(listOf(updatePlace))
    }
}