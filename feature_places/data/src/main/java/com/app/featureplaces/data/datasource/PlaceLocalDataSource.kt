package com.app.featureplaces.data.datasource

import com.app.domain.Error
import com.app.domain.Place
import kotlinx.coroutines.flow.Flow

interface PlaceLocalDataSource {
    val places: Flow<List<Place>>
    suspend fun isEmpty(): Boolean
    fun findById(id: Int): Flow<Place>
    suspend fun save(places: List<Place>): Error?
}