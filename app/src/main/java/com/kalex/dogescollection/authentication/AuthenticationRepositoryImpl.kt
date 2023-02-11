package com.kalex.dogescollection.authentication

import com.kalex.dogescollection.authentication.createaccount.dto.SignUpDTO
import com.kalex.dogescollection.api.DogsApi
import com.kalex.dogescollection.authentication.createaccount.dto.UserResponse
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor (
    private val dogApi : DogsApi
) : AuthenticationRepository {
    override suspend fun createAccount(user: String, password: String, passwordConfirm: String): UserResponse {
        return dogApi.createAccount(SignUpDTO(user, password, passwordConfirm))
    }
}