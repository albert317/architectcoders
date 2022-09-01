package com.app.featureplaces.presentation.ui.detail

import app.cash.turbine.test
import com.app.featureplaces.presentation.testrules.CoroutinesTestRule
import com.app.featureplaces.usecases.FindPlaceUseCase
import com.app.featureplaces.usecases.GetCommentsOfPlaceUseCase
import com.app.featureplaces.usecases.SaveCommentOfPlaceUseCase
import com.app.featureplaces.usecases.SwitchPlaceFavoriteUseCase
import com.app.testshared.sampleComment
import com.app.testshared.samplePlace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var findPlaceUseCase: FindPlaceUseCase

    @Mock
    lateinit var getCommentsOfPlaceUseCase: GetCommentsOfPlaceUseCase

    @Mock
    lateinit var saveCommentOfPlaceUseCase: SaveCommentOfPlaceUseCase

    @Mock
    lateinit var switchPlaceFavoriteUseCase: SwitchPlaceFavoriteUseCase


    private lateinit var vm: DetailViewModel

    private val place = samplePlace.copy(id = 2)

    private val comment = sampleComment.copy(idPlace = 2)
    private val comments = listOf(comment)

    @Before
    fun setup() {
        whenever(findPlaceUseCase(2)).thenReturn(flowOf(place))
        vm = DetailViewModel(
            2,
            findPlaceUseCase,
            getCommentsOfPlaceUseCase,
            saveCommentOfPlaceUseCase,
            switchPlaceFavoriteUseCase
        )
    }

    @Test
    fun `UI is updated with the place on start`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(place = place, urlImage = place.images.random().url), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite action calls the corresponding use case`() = runTest {
        vm.onFavoriteClicked()
        runCurrent()

        verify(switchPlaceFavoriteUseCase).invoke(place)
    }

    @Test
    fun `Send Comment action call the corresponding use case`() = runTest {
        vm.saveCommentClicked("Hello")
        runCurrent()
        verify(saveCommentOfPlaceUseCase).invoke(argThat { commentText == "Hello" })
    }

}