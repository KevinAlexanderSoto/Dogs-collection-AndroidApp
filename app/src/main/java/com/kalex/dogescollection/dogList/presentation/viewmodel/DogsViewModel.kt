package com.kalex.dogescollection.dogList.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalex.dogescollection.dogList.model.data.dto.Data
import com.kalex.dogescollection.dogList.model.networkstates.ViewModelNewsUiState
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

    private val _dogState = MutableStateFlow<ViewModelNewsUiState<Data>>(ViewModelNewsUiState.Loading(true))
    val dogState: StateFlow<ViewModelNewsUiState<Data>>
        get() = _dogState

    fun getAllDogs() {
        viewModelScope.launch {
            dogsUseCase.getAllDogs().collectLatest {
                when (it) {
                    is DogsFlowStatus.Error -> _dogState.value = ViewModelNewsUiState.Error(it.exception)
                    is DogsFlowStatus.Success -> _dogState.value = ViewModelNewsUiState.Success(it.dogsList)
                    is DogsFlowStatus.Loading ->_dogState.value = ViewModelNewsUiState.Loading(true)
                }
            }
        }
    }

}
