package com.kalex.dogescollection.authentication.model

import com.kalex.dogescollection.core.api.DogsApi
import com.kalex.dogescollection.core.model.dto.LogInDTO
import com.kalex.dogescollection.core.model.dto.SignUpDTO
import com.kalex.dogescollection.core.model.dto.UserResponse
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor (
    private val dogApi : DogsApi
) : AuthenticationRepository {
    override suspend fun createAccount(user: String, password: String, passwordConfirm: String): UserResponse {
        return dogApi.createAccount(
            SignUpDTO(
                user,
                password,
                passwordConfirm
            )
        )
    }

    override suspend fun SignIn(user: String, password: String): UserResponse {
        return dogApi.logIn(LogInDTO(user, password))
    }
}