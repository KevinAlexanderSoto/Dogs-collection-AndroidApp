package com.kalex.dogescollection.authentication.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalex.dogescollection.authentication.createaccount.dto.User
import com.kalex.dogescollection.authentication.model.AuthenticationUseCase
import com.kalex.dogescollection.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.common.networkstates.ViewModelNewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationUseCase : AuthenticationUseCase
) : ViewModel() {

    private val _signInState = MutableStateFlow<ViewModelNewsUiState<User>>(
        ViewModelNewsUiState.Loading(true))
    val signInState: StateFlow<ViewModelNewsUiState<User>>
        get() = _signInState

    fun signIn(user : String,password : String) {
        viewModelScope.launch {
            authenticationUseCase.signIn(user,password).collectLatest {
                when (it) {
                    is UseCaseFlowStatus.Success -> _signInState.value = ViewModelNewsUiState.Success(it.data)
                    is UseCaseFlowStatus.Loading ->_signInState.value = ViewModelNewsUiState.Loading(true)
                    is UseCaseFlowStatus.Error -> _signInState.value = ViewModelNewsUiState.Error(it.exception)
                }
            }
        }
    }

}