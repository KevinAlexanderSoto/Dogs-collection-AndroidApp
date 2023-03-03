package com.kalex.dogescollection.authentication.model


import com.kalex.dogescollection.core.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.core.common.networkstates.makeNetworkCallHandler
import com.kalex.dogescollection.core.model.dto.User
import com.kalex.dogescollection.core.model.dto.userDTOtoUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(
    private val Repository: AuthenticationRepositoryImpl
) {
    fun createAccount(user: String, password: String, passwordConfirm: String): Flow<UseCaseFlowStatus<User>> =
        makeNetworkCallHandler {
            val result = Repository.createAccount(user, password, passwordConfirm)
            if (result.is_success) {
                result.body_data.user.userDTOtoUser()
            } else {
                throw Exception(result.message)
            }
        }

    fun signIn(user: String, password: String): Flow<UseCaseFlowStatus<User>> =
        makeNetworkCallHandler {
            val result = Repository.SignIn(user, password)
            if (result.is_success) {
                result.body_data.user.userDTOtoUser()
            } else {
                throw Exception(result.message)
            }
        }
}