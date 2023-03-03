package com.kalex.dogescollection.dogList.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalex.dogescollection.core.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.core.common.networkstates.ViewModelNewsUiState
import com.kalex.dogescollection.core.model.data.alldogs.Data
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import com.kalex.dogescollection.dogList.model.usecase.DogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogCollectionViewModel @Inject constructor(
    private val dogsUseCase : DogsUseCase
):ViewModel() {
    private val _dogCollectionState = MutableStateFlow<ViewModelNewsUiState<Boolean>>(
        ViewModelNewsUiState.Loading(true))
    val currentAddState: StateFlow<ViewModelNewsUiState<Boolean>>
        get() = _dogCollectionState

    private val _getCollectionState = MutableStateFlow<ViewModelNewsUiState<List<Dog>>>(
        ViewModelNewsUiState.Loading(true))
    val getCollectionState: StateFlow<ViewModelNewsUiState<List<Dog>>>
        get() = _getCollectionState

    //TODO : when add a new dog, reload the dog list,
    fun addDogToCollection(id : Long){
        viewModelScope.launch {
            dogsUseCase.addDogToCollection(id).collectLatest {
                when (it) {
                    is UseCaseFlowStatus.Error -> _dogCollectionState.value = ViewModelNewsUiState.Error(it.exception)
                    is UseCaseFlowStatus.Success -> _dogCollectionState.value = ViewModelNewsUiState.Success(it.data)
                    is UseCaseFlowStatus.Loading ->_dogCollectionState.value = ViewModelNewsUiState.Loading(true)
                }
            }
        }
    }

    fun getDogCollection(){
        viewModelScope.launch {
            dogsUseCase.getDogCollection().collectLatest {
                when (it) {
                    is UseCaseFlowStatus.Error -> _getCollectionState.value = ViewModelNewsUiState.Error(it.exception)
                    is UseCaseFlowStatus.Success -> _getCollectionState.value = ViewModelNewsUiState.Success(it.data)
                    is UseCaseFlowStatus.Loading ->_getCollectionState.value = ViewModelNewsUiState.Loading(true)
                }
            }
        }
    }
}