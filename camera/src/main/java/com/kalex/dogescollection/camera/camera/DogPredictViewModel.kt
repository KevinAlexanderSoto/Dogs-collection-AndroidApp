package com.kalex.dogescollection.camera.camera



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalex.dogescollection.core.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.core.common.networkstates.ViewModelNewsUiState
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import com.kalex.dogescollection.dogList.model.usecase.DogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogPredictViewModel @Inject constructor(
    private val dogsUseCase: DogsUseCase
) : ViewModel() {

    private val _dogState = MutableStateFlow<ViewModelNewsUiState<Dog>>(
        ViewModelNewsUiState.Loading(true))
    val dogState: StateFlow<ViewModelNewsUiState<Dog>>
        get() = _dogState

    fun getDogByPredictedId(mlId : String) {
        viewModelScope.launch {
            dogsUseCase.getDogByPredictedId(mlId).collectLatest {
                when (it) {
                    is UseCaseFlowStatus.Error -> _dogState.value = ViewModelNewsUiState.Error(it.exception)
                    is UseCaseFlowStatus.Success -> _dogState.value = ViewModelNewsUiState.Success(it.data)
                    is UseCaseFlowStatus.Loading ->_dogState.value = ViewModelNewsUiState.Loading(true)
                }
            }
        }
    }

}
