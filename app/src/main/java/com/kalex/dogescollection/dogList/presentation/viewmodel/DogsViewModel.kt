package com.kalex.dogescollection.dogList.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalex.dogescollection.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.dogList.model.data.dto.Data
import com.kalex.dogescollection.common.networkstates.ViewModelNewsUiState
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
                    is UseCaseFlowStatus.Error -> _dogState.value = ViewModelNewsUiState.Error(it.exception)
                    is UseCaseFlowStatus.Success -> _dogState.value = ViewModelNewsUiState.Success(it.data)
                    is UseCaseFlowStatus.Loading ->_dogState.value = ViewModelNewsUiState.Loading(true)
                }
            }
        }
    }

}
