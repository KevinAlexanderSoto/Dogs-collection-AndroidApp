package com.kalex.dogescollection.common.networkstates

import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

/**
 * Function to handler network call and errors, this return a flow of type UseCaseFlowStatus <T>
 * @author Kevin Alexander Soto
 *  @param call this is a function to make the request and pass the result or throw an Exception
 * **/
fun <T> makeNetworkCallHandler(
    call : suspend () -> T
) = flow<UseCaseFlowStatus<T>> {
    try {
        emit(UseCaseFlowStatus.Loading(""))
        emit(UseCaseFlowStatus.Success(call()))

    }catch (e:Exception){
        emit(UseCaseFlowStatus.Error(e.message ?: "Unknown") )
    }
    catch (e : UnknownHostException){
        emit( UseCaseFlowStatus.Error(e.message ?: "Unknown"))
    }
}
sealed class ViewModelNewsUiState<T> {
    data class Success<T>(val data: T) : ViewModelNewsUiState<T>()
    data class Loading<T>(val isLoading: Boolean) : ViewModelNewsUiState<T>()
    data class Error<T>(val exception: String) : ViewModelNewsUiState<T>()
}

sealed class UseCaseFlowStatus <T>{
    data class Success<T>(val data :T) : UseCaseFlowStatus<T>()
    data class Error<T>(val exception: String ) : UseCaseFlowStatus<T>()
    data class Loading<T>(val message: String) : UseCaseFlowStatus<T>()
}