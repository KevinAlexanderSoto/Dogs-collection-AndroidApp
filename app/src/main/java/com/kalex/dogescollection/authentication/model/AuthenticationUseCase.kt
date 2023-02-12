package com.kalex.dogescollection.authentication.model

import com.kalex.dogescollection.authentication.createaccount.dto.User
import com.kalex.dogescollection.authentication.createaccount.dto.userDTOtoUser
import com.kalex.dogescollection.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.common.networkstates.makeNetworkCallHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(
    private val Repository: AuthenticationRepositoryImpl
) {
    fun createAccount(user: String, password: String, passwordConfirm: String): Flow<UseCaseFlowStatus<User>> = makeNetworkCallHandler{
        val result = Repository.createAccount(user, password, passwordConfirm)
        if(result.is_success){
            result.body_data.userDTOtoUser()
        }else{
            throw Exception(result.message)
        }
    }
}