package com.movieapppoc.di

import com.movieapppoc.common.kotlin.coroutine.api.DispatcherProvider
import com.movieapppoc.common.kotlin.coroutine.main.CoroutineDispatcherProvider
import com.movieapppoc.movielist.data.repository.MovieListRepositoryImpl
import com.movieapppoc.movielist.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindDispatcherProvider(dispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(
        movieListRepository: MovieListRepositoryImpl
    ): MovieListRepository


}