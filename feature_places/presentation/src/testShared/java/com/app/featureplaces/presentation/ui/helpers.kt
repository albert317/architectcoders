package com.app.featureplaces.presentation.ui


/*fun buildRepositoryWith(
    localData: List<PlaceWithImagePlaces>,
    localDataImages: List<ImagePlace>,
    remoteData: List<RemotePlace>,
): PlacesRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    val localDataSource = PlaceRoomDataSource(FakePlaceDao(localData),FakeImagePlaceDao(localDataImages))
    val remoteDataSource = MovieServerDataSource("1234", FakeRemoteService(remoteData))
    return MoviesRepository(regionRepository, localDataSource, remoteDataSource)
}

fun buildDatabaseMovies(vararg id: Int) = id.map {
    DatabaseMovie(
        id = it,
        title = "Title $it",
        overview = "Overview $it",
        releaseDate = "01/01/2025",
        posterPath = "",
        backdropPath = "",
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        popularity = 5.0,
        voteAverage = 5.1,
        favorite = false
    )
}

fun buildRemoteMovies(vararg id: Int) = id.map {
    RemoteMovie(
        adult = false,
        backdropPath = "",
        genreIds = emptyList(),
        id = it,
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        overview = "Overview $it",
        popularity = 5.0,
        posterPath = "",
        releaseDate = "01/01/2025",
        title = "Title $it",
        video = false,
        voteAverage = 5.1,
        voteCount = 10
    )
}*/