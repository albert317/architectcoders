package com.app.featureplaces.presentation.ui.detail

import app.cash.turbine.test
import com.app.domain.Comment
import com.app.domain.Place
import com.app.featureplaces.data.CommentsRepository
import com.app.featureplaces.data.PlacesRepository
import com.app.featureplaces.data.RegionRepository
import com.app.featureplaces.presentation.*
import com.app.featureplaces.presentation.testrules.CoroutinesTestRule
import com.app.featureplaces.usecases.FindPlaceUseCase
import com.app.featureplaces.usecases.GetCommentsOfPlaceUseCase
import com.app.featureplaces.usecases.SaveCommentOfPlaceUseCase
import com.app.featureplaces.usecases.SwitchPlaceFavoriteUseCase
import com.app.testshared.samplePlace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `UI is updater with the place on start`() = runTest {
        val vm = buildViewModelWith(
            id = 2,
            localData = listOf(samplePlace.copy(1), samplePlace.copy(2), samplePlace.copy(3))
        )
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(2, awaitItem().place!!.id)
            cancel()
        }
    }


    @Test
    fun `Favorite is updated in local data source`() = runTest {
        val vm = buildViewModelWith(
            id = 2,
            localData = listOf(samplePlace.copy(1), samplePlace.copy(2))
        )

        vm.onFavoriteClicked()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(
                UiState(place = samplePlace.copy(id = 2, favorite = false), urlImage = ""),
                awaitItem()
            )
            assertEquals(
                UiState(place = samplePlace.copy(id = 2, favorite = true), urlImage = ""),
                awaitItem()
            )
        }
    }

    private fun buildViewModelWith(
        id: Int,
        localData: List<Place> = emptyList(),
        remoteData: List<Place> = emptyList(),
        commentData: List<Comment> = emptyList()
    ): DetailViewModel {
        val locationDataSource = FakeLocationDataSource()
        val permissionChecker = FakePermissionChecker()
        val regionRepository = RegionRepository(locationDataSource, permissionChecker)

        val localDataSource = FakeLocalDataSource().apply { places.value = localData }
        val remoteDataSource = FakeRemoteDataSource().apply { places = remoteData }
        val placeRepository = PlacesRepository(localDataSource, remoteDataSource, regionRepository)


        val commentFirebaseServerDataSource =
            FakeCommentFirebaseRemoteDataSource().apply { comments = flowOf(commentData) }
        val commentRepository = CommentsRepository(commentFirebaseServerDataSource)


        val findPlaceUseCase = FindPlaceUseCase(placeRepository)
        val getCommentsOfPlaceUseCase = GetCommentsOfPlaceUseCase(commentRepository)
        val saveCommentOfPlaceUseCase = SaveCommentOfPlaceUseCase(commentRepository)
        val switchPlaceFavoriteUseCase = SwitchPlaceFavoriteUseCase(placeRepository)
        return DetailViewModel(
            id,
            findPlaceUseCase,
            getCommentsOfPlaceUseCase,
            saveCommentOfPlaceUseCase,
            switchPlaceFavoriteUseCase
        )
    }
}