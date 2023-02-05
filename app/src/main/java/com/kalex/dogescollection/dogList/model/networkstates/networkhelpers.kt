package com.kalex.dogescollection.dogList.model.networkstates


sealed class ViewModelNewsUiState<T> {
    data class Success<T>(val data: T) : ViewModelNewsUiState<T>()
    data class Loading<T>(val isLoading: Boolean) : ViewModelNewsUiState<T>()
    data class Error<T>(val exception: String) : ViewModelNewsUiState<T>()
}