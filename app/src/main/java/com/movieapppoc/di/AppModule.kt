package com.movieapppoc.di

import android.app.Application
import androidx.room.Room
import com.movieapppoc.movielist.data.local.MovieDao
import com.movieapppoc.movielist.data.local.MovieDatabase
import com.movieapppoc.movielist.data.local.MovieDatabase.Companion.DATABASE_NAME
import com.pratham.networkmodule.BaseUrl
import com.pratham.networkmodule.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @BaseUrl
    @Provides
    @Singleton
    fun provideBaseUrl(): String = MovieApi.BASE_URL

    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun providesMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao
    }
}