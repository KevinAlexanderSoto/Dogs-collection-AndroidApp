package com.kalex.dogescollection.authentication.createaccount.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalex.dogescollection.authentication.model.AuthenticationUseCase
import com.kalex.dogescollection.authentication.createaccount.dto.User
import com.kalex.dogescollection.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.common.networkstates.ViewModelNewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val authenticationUseCase : AuthenticationUseCase
): ViewModel() {
    private val _authenticationState = MutableStateFlow<ViewModelNewsUiState<User>>(ViewModelNewsUiState.Loading(true))
    val authenticationState: StateFlow<ViewModelNewsUiState<User>>
        get() = _authenticationState

    fun createAccount(user : String,password : String,passwordConfirm : String) {
        viewModelScope.launch {
            authenticationUseCase.createAccount(user,password,passwordConfirm).collectLatest {
                when (it) {
                    is UseCaseFlowStatus.Success -> _authenticationState.value = ViewModelNewsUiState.Success(it.data)
                    is UseCaseFlowStatus.Loading ->_authenticationState.value = ViewModelNewsUiState.Loading(true)
                    is UseCaseFlowStatus.Error -> _authenticationState.value = ViewModelNewsUiState.Error(it.exception)
                }
            }
        }
    }
}