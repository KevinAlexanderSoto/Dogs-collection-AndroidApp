package com.kalex.dogescollection.common.networkstates

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kalex.dogescollection.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * Function to handler network call and errors, this return a flow of type UseCaseFlowStatus <T>
 * @author Kevin Alexander Soto
 *  @param call this is a function to make the request and pass the result or throw an Exception
 * **/
fun <T> makeNetworkCallHandler(
    call: suspend () -> T
) = flow<UseCaseFlowStatus<T>> {
    try {
        emit(UseCaseFlowStatus.Loading(""))
        emit(UseCaseFlowStatus.Success(call()))

    } catch (e: UnknownHostException) {
        emit(UseCaseFlowStatus.Error(R.string.Internet_error_message))
    } catch (e: HttpException) {
        val errorMessage = when (e.code()) {
            401 -> R.string.wrong_user_or_password_error_message
            else -> R.string.unknown_error_message
        }
        emit(UseCaseFlowStatus.Error(errorMessage))
    } catch (e: Exception) {

        val errorMessage = when (e.message) {
            "sign_up_error" -> R.string.sign_up_error
            "sign_in_error" -> R.string.sign_in_error
            "user_already_exists" -> R.string.user_already_exists
            else -> R.string.unknown_error_message
        }

        emit(UseCaseFlowStatus.Error(errorMessage))
    }

}

fun <T> Fragment.handleViewModelState(
    call: Flow<ViewModelNewsUiState<T>>,
    onSuccess: (T) -> Unit,
    onLoading: (Boolean) -> Unit,
    onError: (Int) -> Unit
) {
    lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            call.collectLatest {
                when (it) {
                    is ViewModelNewsUiState.Error -> onError.invoke(it.exception)
                    is ViewModelNewsUiState.Loading -> onLoading.invoke(it.isLoading)
                    is ViewModelNewsUiState.Success -> onSuccess.invoke(it.data)
                }
            }
        }
    }
}

sealed class ViewModelNewsUiState<T> {
    data class Success<T>(val data: T) : ViewModelNewsUiState<T>()
    data class Loading<T>(val isLoading: Boolean) : ViewModelNewsUiState<T>()
    data class Error<T>(val exception: Int) : ViewModelNewsUiState<T>()
}

sealed class UseCaseFlowStatus<T> {
    data class Success<T>(val data: T) : UseCaseFlowStatus<T>()
    data class Error<T>(val exception: Int) : UseCaseFlowStatus<T>()
    data class Loading<T>(val message: String) : UseCaseFlowStatus<T>()
}