package com.app.featureplaces.usecases


import com.app.featureplaces.data.PlacesRepository
import com.app.testshared.samplePlace
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SwitchPlaceFavoriteUseCaseTest {

    @Test
    fun `Invoke calls places repository`(): Unit = runBlocking {
        val place = samplePlace.copy(id = 1)
        val placesRepository = mock<PlacesRepository>()
        val switchPlaceFavoriteUseCase = SwitchPlaceFavoriteUseCase(placesRepository)

        switchPlaceFavoriteUseCase(place)

        verify(placesRepository).switchFavorite(place)
    }
}
