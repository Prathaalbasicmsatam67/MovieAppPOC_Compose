package com.movieapppoc.movielist.data.remote

import com.pratham.networkmodule.MovieApi
import com.pratham.networkmodule.response.MovieListDto
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieApi: MovieApi
) {
    suspend fun getMoviesList(
        category: String,
        page: Int
    ): MovieListDto = movieApi.getMoviesList(category, page, MovieApi.API_KEY)
}