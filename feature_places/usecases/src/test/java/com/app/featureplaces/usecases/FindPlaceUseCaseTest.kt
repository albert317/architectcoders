package com.app.featureplaces.usecases

import com.app.testshared.samplePlace
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


class FindPlaceUseCaseTest {

    @Test
    fun `Invoke calls places repository`(): Unit = runBlocking {
        val place = flowOf(samplePlace.copy(id = 1))
        val findPlacesUseCase = FindPlaceUseCase(mock() {
            on { findById((1)) } doReturn (place)
        })

        val result = findPlacesUseCase(1)

        assertEquals(result, place)
    }
}