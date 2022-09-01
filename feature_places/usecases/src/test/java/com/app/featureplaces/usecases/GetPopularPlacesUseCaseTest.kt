package com.app.featureplaces.usecases

import com.app.testshared.samplePlace
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


class GetPopularPlacesUseCaseTest {

    @Test
    fun `Invoke call places repository`(): Unit = runBlocking {
        val places = flowOf(listOf(samplePlace.copy(id = 1)))
        val getPopularPlacesUseCase = GetPopularPlacesUseCase(mock {
            on { getPopularPlaces() } doReturn places
        })

        val result = getPopularPlacesUseCase()

        Assert.assertEquals(places, result)
    }
}