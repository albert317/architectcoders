package com.app.featureplaces.presentation.data.server

import retrofit2.http.GET

interface RemoteService {
    @GET("/list")
    suspend fun listPopularMovies(): RemoteResult
}