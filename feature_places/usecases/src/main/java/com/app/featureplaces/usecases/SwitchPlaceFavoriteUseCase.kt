package com.app.featureplaces.usecases

import com.app.featureplaces.data.PlacesRepository
import com.app.domain.Place
import javax.inject.Inject

class SwitchPlaceFavoriteUseCase  @Inject constructor(private val placesRepository: PlacesRepository) {
    suspend operator fun invoke(place: Place) = placesRepository.switchFavorite(place)
}