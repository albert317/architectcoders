package com.app.featureplaces.usecases

import com.app.featureplaces.data.PlacesRepository
import com.app.domain.Place
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindPlaceUseCase @Inject constructor(private val repository: PlacesRepository) {
    operator fun invoke(id: Int): Flow<Place> = repository.findById(id)
}