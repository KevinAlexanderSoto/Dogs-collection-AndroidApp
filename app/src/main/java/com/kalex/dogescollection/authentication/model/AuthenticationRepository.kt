package com.kalex.dogescollection.authentication.model

import com.kalex.dogescollection.core.model.dto.UserResponse

interface AuthenticationRepository {
    suspend fun createAccount(user: String, password: String, passwordConfirm: String): UserResponse
    suspend fun SignIn(user: String, password: String): UserResponse
}