package com.app.featureplaces.usecases

import com.app.featureplaces.data.CommentsRepository
import com.app.domain.Comment
import com.app.domain.Error
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCommentOfPlaceUseCase @Inject constructor(private val repository: CommentsRepository) {
    operator fun invoke(comment: Comment): Flow<Error?> = repository.saveCommentOfPlace(comment)
}