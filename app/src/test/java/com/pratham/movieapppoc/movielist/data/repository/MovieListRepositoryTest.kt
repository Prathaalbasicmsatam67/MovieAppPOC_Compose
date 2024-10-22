package com.pratham.movieapppoc.movielist.data.repository

import app.cash.turbine.test
import com.movieapppoc.common.kotlin.coroutine.api.DispatcherProvider
import com.movieapppoc.movielist.data.local.MovieEntity
import com.movieapppoc.movielist.data.local.MovieLocalDataSource
import com.movieapppoc.movielist.data.remote.MovieRemoteDataSource
import com.movieapppoc.movielist.data.repository.MovieListRepositoryImpl
import com.movieapppoc.movielist.domain.repository.MovieListRepository
import com.movieapppoc.movielist.util.Resource
import com.movieapppoc.movielist.util.network.ConnectivityProvider
import com.pratham.movieapppoc.common.TestDispatcherProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(MockitoJUnitRunner::class)
class MovieListRepositoryTest {


    private lateinit var sut : MovieListRepository

    @Mock
    private lateinit var movieRemoteDataSource: MovieRemoteDataSource

    @Mock
    private lateinit var movieLocalDataSource: MovieLocalDataSource

    @Mock
    private lateinit var connectivityProvider: ConnectivityProvider

    @Mock
    private val dispatcherProvider: DispatcherProvider = TestDispatcherProvider()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        sut = MovieListRepositoryImpl(
            movieRemoteDataSource = movieRemoteDataSource,
            movieLocalDataSource = movieLocalDataSource,
            connectivityProvider = connectivityProvider,
            dispatcherProvider = dispatcherProvider
        )
    }

    @Test
    fun `Given noNetwork should load from local datasource`(): Unit = runBlocking {

        // Given
        val movies = listOf(
            MovieEntity(
                adult = true,
                backdrop_path = "URL",
                genre_ids = "1,2",
                original_language = "en",
                original_title = "Movie title",
                overview = "bbkjbkj bkjbk jbkbj",
                popularity = 0.3,
                poster_path = "url",
                release_date = "09/01/2023",
                title = "movieTitle",
                video = true,
                vote_average = 0.9,
                vote_count = 78,
                id = 1,
                category = "Category",
            )
        )
        val category = "popular"
        `when`(connectivityProvider.isConnected()).thenReturn(false)
        `when`(movieLocalDataSource.getMovieByCategory(category)).thenReturn(movies)

        // When
        val result = sut.getMovieList(category, 1)

        // Then
        result.test {
//            expectMostRecentItem()
            // start loading
            Assert.assertTrue(awaitItem() is Resource.Loading)
            // load local list of items
            Assert.assertTrue(awaitItem() is Resource.Success)
            // stop loading
            Assert.assertTrue(awaitItem() is Resource.Loading)
            awaitComplete()
        }
    }
}