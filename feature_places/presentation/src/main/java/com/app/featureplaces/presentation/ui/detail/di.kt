package com.app.featureplaces.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.app.featureplaces.presentation.di.PlaceId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {

    @Provides
    @ViewModelScoped
    @PlaceId
    fun providePlaceId(savedStateHandle: SavedStateHandle) =
        DetailPlaceFragmentArgs.fromSavedStateHandle(savedStateHandle).idPlace

    //private val placeId = DetailPlaceFragmentArgs.fromSavedStateHandle(savedStateHandle).idPlace
}