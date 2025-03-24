package com.movieapppoc.di

import com.movieapppoc.movielist.data.repository.MovieListRepositoryImpl
import com.movieapppoc.movielist.domain.repository.MovieListRepository
import com.pratham.coroutine_common.CoroutineDispatcherProvider
import com.pratham.coroutine_common.api.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


//    @Binds
//    @Singleton
//    abstract fun bindDispatcherProvider(dispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(
        movieListRepository: MovieListRepositoryImpl
    ): MovieListRepository


}