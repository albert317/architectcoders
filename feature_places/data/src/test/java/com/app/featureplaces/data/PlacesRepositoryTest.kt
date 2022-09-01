package com.app.featureplaces.data

import arrow.core.right
import com.app.featureplaces.data.datasource.PlaceLocalDataSource
import com.app.featureplaces.data.datasource.PlaceRemoteDataSource
import com.app.testshared.samplePlace
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class PlacesRepositoryTest {

    @Mock
    lateinit var localDataSource: PlaceLocalDataSource

    @Mock
    lateinit var remoteDataSource: PlaceRemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var placesRepository: PlacesRepository

    private val localPlaces = flowOf(listOf(samplePlace.copy(1)))

    @Before
    fun setup() {
        whenever(localDataSource.places).thenReturn(localPlaces)
        placesRepository = PlacesRepository(
            regionRepository = regionRepository,
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun `Popular places are taken from local data source is available`(): Unit = runBlocking {

        val result = placesRepository.getPopularPlaces()

        assertEquals(localPlaces, result)
    }

    @Test
    fun `Popular place are saved to local data source when it's empty`(): Unit = runBlocking {
        val remotePlaces = listOf(samplePlace.copy(2))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(regionRepository.findLastRegion()).thenReturn(RegionRepository.DEFAULT_REGION)
        whenever(remoteDataSource.findPopularPlaces()).thenReturn(remotePlaces.right())

        placesRepository.requestPopularMovies()

        verify(localDataSource).save(remotePlaces)
    }

    @Test
    fun `Finding a place by id is done in local data source`(): Unit = runBlocking {
        val place = flowOf(samplePlace.copy(id = 5))
        whenever(localDataSource.findById(5)).thenReturn(place)

        val result = placesRepository.findById(5)

        assertEquals(place, result)
    }

    @Test
    fun `Switching favorite updates local data source`(): Unit = runBlocking {
        val place = samplePlace.copy(id = 6)

        placesRepository.switchFavorite(place)

        verify(localDataSource).save(argThat { get(0).id == 6 })
    }

    @Test
    fun `Switching favorite marks as favorite an unfavorite place`(): Unit = runBlocking {
        val place = samplePlace.copy(favorite = false)

        placesRepository.switchFavorite(place)

        verify(localDataSource).save(argThat { get(0).favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite an favorite place`(): Unit = runBlocking {
        val place = samplePlace.copy(favorite = true)
        placesRepository.switchFavorite(place)

        verify(localDataSource).save(argThat { !get(0).favorite })
    }
}
