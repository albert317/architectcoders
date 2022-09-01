package com.app.featureplaces.usecases

import com.app.featureplaces.data.PlacesRepository
import com.app.domain.Error
import javax.inject.Inject

class RequestPopularPlacesUseCase @Inject constructor(private val placesRepository: PlacesRepository) {
    suspend operator fun invoke(): Error? {
        return placesRepository.requestPopularMovies()
    }
}