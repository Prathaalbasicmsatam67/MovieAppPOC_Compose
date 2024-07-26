package com.movieapppoc.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapppoc.movielist.domain.repository.MovieListRepository
import com.movieapppoc.movielist.presentation.MovieListState
import com.movieapppoc.movielist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movieId")

    private var _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    init {
        getMovie(movieId ?: -1)
    }

    private fun getMovie(movieId: Int) {
        viewModelScope.launch {
            movieListRepository.getMovie(movieId)
                .onStart {
                    _detailsState.update {
                        it.copy(isLoading = true)
                    }
                }
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> _detailsState.update {
                            it.copy(isLoading = false)
                        }

                        is Resource.Loading -> _detailsState.update {
                            it.copy(isLoading = result.isLoading)
                        }

                        is Resource.Success -> {
                            result.data?.let { movie ->
                                _detailsState.update {
                                    it.copy(isLoading = false, movie = movie)
                                }
                            }

                        }
                    }
                }
        }


    }

}