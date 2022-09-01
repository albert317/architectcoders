package com.app.featureplaces.usecases

import com.app.featureplaces.data.CommentsRepository
import com.app.testshared.sampleComment
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SaveCommentOfPlaceUseCaseTest {

    @Test
    fun `Invoke calls places repository`(): Unit = runBlocking {
        val comment = sampleComment.copy(id = "123")
        val commentsRepository = mock<CommentsRepository>()
        val saveCommentOfPlaceUseCase = SaveCommentOfPlaceUseCase(commentsRepository)

        saveCommentOfPlaceUseCase(comment)

        verify(commentsRepository).saveCommentOfPlace(comment)
    }
}