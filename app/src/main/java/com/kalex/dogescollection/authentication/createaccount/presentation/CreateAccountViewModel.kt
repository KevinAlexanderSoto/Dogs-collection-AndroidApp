package com.kalex.dogescollection.authentication.createaccount.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalex.dogescollection.authentication.AuthenticationUseCase
import com.kalex.dogescollection.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.common.networkstates.ViewModelNewsUiState
import com.kalex.dogescollection.dogList.model.data.dto.Data
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateAccountViewModel @Inject constructor(
    val authenticationUseCase : AuthenticationUseCase
): ViewModel() {
    private val _dogState = MutableStateFlow<ViewModelNewsUiState<Data>>(ViewModelNewsUiState.Loading(true))
    val dogState: StateFlow<ViewModelNewsUiState<Data>>
        get() = _dogState

    fun createAccount(user : String,password : String,passwordConfirm : String) {
        viewModelScope.launch {
            authenticationUseCase.createAccount(user,password,passwordConfirm).collectLatest {
                when (it) {
                    is UseCaseFlowStatus.Error -> _dogState.value = ViewModelNewsUiState.Error(it.exception)
                    is UseCaseFlowStatus.Success -> _dogState.value = ViewModelNewsUiState.Success(it.data)
                    is UseCaseFlowStatus.Loading ->_dogState.value = ViewModelNewsUiState.Loading(true)
                }
            }
        }
    }
}