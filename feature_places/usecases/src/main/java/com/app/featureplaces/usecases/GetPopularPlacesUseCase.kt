package com.app.featureplaces.usecases

import com.app.featureplaces.data.PlacesRepository
import javax.inject.Inject

class GetPopularPlacesUseCase  @Inject constructor(private val repository: PlacesRepository) {
    operator fun invoke() = repository.getPopularPlaces()
}