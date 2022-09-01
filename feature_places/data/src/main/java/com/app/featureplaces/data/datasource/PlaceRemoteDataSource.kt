package com.app.featureplaces.data.datasource

import arrow.core.Either
import com.app.domain.Error
import com.app.domain.Place

interface PlaceRemoteDataSource {
    suspend fun findPopularPlaces(): Either<Error, List<Place>>
}