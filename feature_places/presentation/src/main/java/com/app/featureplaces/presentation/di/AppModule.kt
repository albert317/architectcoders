package com.app.featureplaces.presentation.di

import android.app.Application
import androidx.room.Room
import com.app.featureplaces.data.PermissionChecker
import com.app.featureplaces.data.datasource.CommentFirebaseRemoteDataSource
import com.app.featureplaces.data.datasource.LocationDataSource
import com.app.featureplaces.data.datasource.PlaceLocalDataSource
import com.app.featureplaces.data.datasource.PlaceRemoteDataSource
import com.app.featureplaces.presentation.R
import com.app.featureplaces.presentation.data.AndroidPermissionChecker
import com.app.featureplaces.presentation.data.PlayServicesLocationDataSource
import com.app.featureplaces.presentation.data.database.PlaceDataBase
import com.app.featureplaces.presentation.data.database.PlaceRoomDataSource
import com.app.featureplaces.presentation.data.firebase.CommentFirebaseServerDataSource
import com.app.featureplaces.presentation.data.server.PlaceServerDataSource
import com.app.featureplaces.presentation.data.server.RemoteService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        PlaceDataBase::class.java,
        "place_db"
    ).build()

    @Provides
    @Singleton
    fun providePlaceDao(db: PlaceDataBase) = db.placeDao()

    @Provides
    @Singleton
    fun provideImagePlaceDao(db: PlaceDataBase) = db.imagePlaceDao()

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String = "https://32b6eea1-af9b-4ef7-8a90-f4fd21208f77.mock.pstmn.io"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    @Provides
    @Singleton
    fun provideRemoteService(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): RemoteService {

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun bindLocalDataSource(localDataSource: PlaceRoomDataSource): PlaceLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: PlaceServerDataSource): PlaceRemoteDataSource

    @Binds
    abstract fun bindRemoteFirebaseComments(commentFirebaseServerDataSource: CommentFirebaseServerDataSource): CommentFirebaseRemoteDataSource

    @Binds
    abstract fun bindingLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker
}
