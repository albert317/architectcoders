package com.app.featureplaces.usecases

import com.app.featureplaces.data.CommentsRepository
import javax.inject.Inject

class GetCommentsOfPlaceUseCase @Inject constructor(private val repository: CommentsRepository) {
    suspend operator fun invoke(idPlace: Int) = repository.getCommentsOfPlace(idPlace)
}