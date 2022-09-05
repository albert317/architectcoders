package com.app.featureplaces.presentation.ui.listplaces

import app.cash.turbine.test
import com.app.domain.Place
import com.app.featureplaces.data.PlacesRepository
import com.app.featureplaces.data.RegionRepository
import com.app.featureplaces.presentation.testrules.CoroutinesTestRule
import com.app.featureplaces.presentation.FakeLocalDataSource
import com.app.featureplaces.presentation.FakeLocationDataSource
import com.app.featureplaces.presentation.FakePermissionChecker
import com.app.featureplaces.presentation.FakeRemoteDataSource
import com.app.featureplaces.presentation.ui.listplaces.ListPlacesViewModel.UiState
import com.app.featureplaces.usecases.GetPopularPlacesUseCase
import com.app.featureplaces.usecases.RequestPopularPlacesUseCase
import com.app.testshared.samplePlace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()


    @Test
    fun `Data is loaded from server when local source is empty`() = runTest {
        val remoteData = listOf(samplePlace.copy(1), samplePlace.copy(2))
        val vm = buildViewModelWith(remoteData = remoteData)

        vm.onUiReady()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(places = emptyList()), awaitItem())
            assertEquals(UiState(places = emptyList(), loading = true), awaitItem())
            assertEquals(UiState(places = emptyList(), loading = false), awaitItem())
            assertEquals(UiState(places = remoteData, loading = false), awaitItem())
            cancel()
        }
    }


    @Test
    fun `Data is loaded from local source when available`() = runTest {
        val localData = listOf(samplePlace.copy(10), samplePlace.copy(11))
        val remoteData = listOf<Place>() // listOf(samplePlace.copy(1), samplePlace.copy(2))

        val vm = buildViewModelWith(localData, remoteData)

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(places = localData), awaitItem())
        }
    }

    private fun buildViewModelWith(
        localData: List<Place> = emptyList(),
        remoteData: List<Place> = emptyList()
    ): ListPlacesViewModel {
        val locationDataSource = FakeLocationDataSource()
        val permissionChecker = FakePermissionChecker()
        val regionRepository = RegionRepository(locationDataSource, permissionChecker)

        val localDataSource = FakeLocalDataSource().apply { places.value = localData }
        val remoteDataSource = FakeRemoteDataSource().apply { places = remoteData }
        val placesRepository = PlacesRepository(localDataSource, remoteDataSource, regionRepository)

        val getPopularPlacesUseCase = GetPopularPlacesUseCase(placesRepository)
        val requestPopularPlacesUseCase = RequestPopularPlacesUseCase(placesRepository)
        return ListPlacesViewModel(getPopularPlacesUseCase, requestPopularPlacesUseCase)
    }
}