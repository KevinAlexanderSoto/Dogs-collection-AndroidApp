package com.kalex.dogescollection.dogList.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalex.dogescollection.dogList.model.data.dto.Data
import com.kalex.dogescollection.dogList.model.usecase.DogsFlowStatus
import com.kalex.dogescollection.dogList.model.usecase.DogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val dogsUseCase: DogsUseCase
) : ViewModel() {

    private val _dogState = MutableStateFlow<LatestNewsUiState>(LatestNewsUiState.Loading(true))
    val dogState: StateFlow<LatestNewsUiState>
        get() = _dogState

    fun getAllDogs() {
        viewModelScope.launch {
            dogsUseCase.getAllDogs().collectLatest {
                when (it) {
                    is DogsFlowStatus.Error -> TODO()
                    is DogsFlowStatus.Success ->{
                        _dogState.value =
                            LatestNewsUiState.Success(it.dogsList)
                    }
                    is DogsFlowStatus.Loading ->_dogState.value = LatestNewsUiState.Loading(true)
                }
            }
        }
    }

}

sealed class LatestNewsUiState {
    data class Success(val dog: Data) : LatestNewsUiState()
    data class Loading(val isLoading: Boolean) : LatestNewsUiState()
    data class Error(val exception: Throwable) : LatestNewsUiState()
}