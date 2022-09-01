package com.app.featureplaces.usecases

import com.app.featureplaces.data.PlacesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestPopularPlacesUseCaseTest {
    @Test
    fun `Invoke calls places repository`(): Unit = runBlocking {
        val placesRepository = mock<PlacesRepository>()
        val requestPopularPlacesUseCase = RequestPopularPlacesUseCase(placesRepository)

        requestPopularPlacesUseCase()

        verify(placesRepository).requestPopularMovies()
    }
}