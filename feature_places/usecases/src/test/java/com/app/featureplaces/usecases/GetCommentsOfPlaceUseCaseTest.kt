package com.app.featureplaces.usecases

import arrow.core.right
import com.app.testshared.sampleComment
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetCommentsOfPlaceUseCaseTest {

    @Test
    fun `Invoke comments of place repository`(): Unit = runBlocking {
        val comments = flowOf(listOf(sampleComment.copy(idPlace = 3)))
        val getCommentsOfPlaceUseCase = GetCommentsOfPlaceUseCase(mock {
            onBlocking { getCommentsOfPlace(3) } doReturn comments.right()
        })

        val result = getCommentsOfPlaceUseCase(3)

        assertEquals(comments.right(), result)
    }
}