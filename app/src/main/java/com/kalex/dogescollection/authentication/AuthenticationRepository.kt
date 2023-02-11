package com.kalex.dogescollection.authentication

import com.kalex.dogescollection.authentication.createaccount.dto.UserResponse

interface AuthenticationRepository {
    suspend fun createAccount(user: String, password: String, passwordConfirm: String): UserResponse
}