package com.app.featureplaces.presentation.ui

import arrow.core.Either
import arrow.core.right
import com.app.domain.Comment
import com.app.domain.Error
import com.app.domain.Place
import com.app.featureplaces.data.PermissionChecker
import com.app.featureplaces.data.datasource.CommentFirebaseRemoteDataSource
import com.app.featureplaces.data.datasource.LocationDataSource
import com.app.featureplaces.data.datasource.PlaceLocalDataSource
import com.app.featureplaces.data.datasource.PlaceRemoteDataSource
import com.app.featureplaces.presentation.data.database.ImagePlace
import com.app.featureplaces.presentation.data.database.ImagePlaceDao
import com.app.featureplaces.presentation.data.database.PlaceDao
import com.app.featureplaces.presentation.data.database.PlaceWithImagePlaces
import com.app.featureplaces.presentation.data.server.RemotePlace
import com.app.featureplaces.presentation.data.server.RemoteResult
import com.app.featureplaces.presentation.data.server.RemoteService
import com.app.testshared.sampleComment
import com.app.testshared.samplePlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import com.app.featureplaces.presentation.data.database.Place as DatabasePlace


val defaultFakePlaces = listOf(
    samplePlace.copy(1),
    samplePlace.copy(2),
    samplePlace.copy(3),
    samplePlace.copy(4)
)

val defaultComments = flowOf(
    listOf(
        sampleComment.copy(idPlace = 1),
        sampleComment.copy(idPlace = 1),
        sampleComment.copy(idPlace = 1),
        sampleComment.copy(idPlace = 1),
        sampleComment.copy(idPlace = 1),
        sampleComment.copy(idPlace = 1)
    )
)


class FakeLocalDataSource : PlaceLocalDataSource {

    val inMemoryPlaces = MutableStateFlow<List<Place>>(emptyList())

    override val places = inMemoryPlaces

    private lateinit var findPlaceFlow: MutableStateFlow<Place>

    override suspend fun isEmpty() = places.value.isEmpty()

    override fun findById(id: Int): Flow<Place> {
        findPlaceFlow = MutableStateFlow(inMemoryPlaces.value.first { it.id == id })
        return findPlaceFlow
    }

    override suspend fun save(places: List<Place>): Error? {
        inMemoryPlaces.value = places
        if (::findPlaceFlow.isInitialized) {
            places.firstOrNull() { it.id == findPlaceFlow.value.id }
                ?.let { findPlaceFlow.value = it }
        }
        return null
    }
}

class FakeRemoteDataSource : PlaceRemoteDataSource {

    var places = defaultFakePlaces

    override suspend fun findPopularPlaces() = places.right()
}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}


class FakeCommentFirebaseRemoteDataSource : CommentFirebaseRemoteDataSource {
    var comments = defaultComments
    val inMemoryComments = MutableStateFlow<List<Comment>>(emptyList())

    private lateinit var findCommentFlow: MutableStateFlow<Comment>

    override suspend fun getCommentsOfPlace(idPlace: Int): Either<Error, Flow<List<Comment>>> {
        findCommentFlow = MutableStateFlow(inMemoryComments.value.first { it.idPlace == idPlace })
        return comments.right()
    }

    override fun saveCommentOfPlace(comment: Comment): Flow<Error?> {
        inMemoryComments.value = listOf(comment)
        if (::findCommentFlow.isInitialized) {
            listOf(comment).firstOrNull() { it.idPlace == findCommentFlow.value.idPlace }
                ?.let { findCommentFlow.value = it }
        }
        return flowOf(null)
    }
}


//

class FakePlaceDao(places: List<PlaceWithImagePlaces> = emptyList()) : PlaceDao {
    private val inMemoryPlaces = MutableStateFlow(places)
    private lateinit var findPlaceFlow: MutableStateFlow<PlaceWithImagePlaces>

    override fun getAll(): Flow<List<PlaceWithImagePlaces>> = inMemoryPlaces

    override fun findById(idPlace: Int): Flow<PlaceWithImagePlaces> {
        findPlaceFlow = MutableStateFlow(inMemoryPlaces.value.first { it.place.id == idPlace })
        return findPlaceFlow
    }

    override suspend fun placeCount(): Int = inMemoryPlaces.value.size

    /* override suspend fun insertPlaces(places: List<PlaceWithImagePlaces>) {
        inMemoryPlaces.value = places
    }*/
    override suspend fun insertPlaces(places: List<DatabasePlace>) {
        inMemoryPlaces.value = places.fromDataInDao()
    }
}

class FakeImagePlaceDao(images: List<ImagePlace> = emptyList()) : ImagePlaceDao {
    private val inMemoryImages = MutableStateFlow(images)

    override suspend fun insertImageOfPlaces(imagesPlaces: List<ImagePlace>) {
        inMemoryImages.value = imagesPlaces
    }
}

class FakePlaceRemoteService(private val places: List<RemotePlace> = emptyList()) : RemoteService {
    override suspend fun listPopularMovies() = RemoteResult(
        1,
        places,
        1,
        places.size
    )
}

@JvmName("toDomainModelPlaceWithImagePlaces")
private fun List<DatabasePlace>.fromDataInDao(): List<PlaceWithImagePlaces> =
    map { it.fromDataInDao() }

private fun DatabasePlace.fromDataInDao() = PlaceWithImagePlaces(
    place = DatabasePlace(
        id = id,
        name = name,
        shortDescription = shortDescription,
        largeDescription = largeDescription,
        location = location,
        latitude = latitude,
        longitude = longitude,
        favorite = favorite
    ),
    listOfImages = listOf(
        ImagePlace(
            id = 0,
            idPlace = id,
            "",
            0
        )
    )
)