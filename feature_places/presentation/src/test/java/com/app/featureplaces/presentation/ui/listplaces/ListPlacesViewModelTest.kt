package com.app.featureplaces.presentation.ui.listplaces

import app.cash.turbine.test
import com.app.featureplaces.presentation.testrules.CoroutinesTestRule
import com.app.featureplaces.presentation.ui.listplaces.ListPlacesViewModel.UiState
import com.app.featureplaces.usecases.GetPopularPlacesUseCase
import com.app.featureplaces.usecases.RequestPopularPlacesUseCase
import com.app.testshared.samplePlace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListPlacesViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getPopularPlacesUseCase: GetPopularPlacesUseCase

    @Mock
    lateinit var requestPopularPlacesUseCase: RequestPopularPlacesUseCase

    private lateinit var vm: ListPlacesViewModel

    private var places = listOf(samplePlace.copy(id = 1))


    @Before
    fun setUp() {
        whenever(getPopularPlacesUseCase()).thenReturn(flowOf(places))
        vm = ListPlacesViewModel(getPopularPlacesUseCase, requestPopularPlacesUseCase)
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(places = places), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is show when screen start an hidden when it finishes requesting places`() =
        runTest {
            vm.onUiReady()

            vm.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(places = places), awaitItem())
                assertEquals(UiState(places = places, loading = true), awaitItem())
                assertEquals(UiState(places = places, loading = false), awaitItem())
                cancel()
            }
        }
}