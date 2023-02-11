package com.kalex.dogescollection.api

import com.kalex.dogescollection.authentication.createaccount.dto.SignUpDTO
import com.kalex.dogescollection.authentication.createaccount.dto.UserResponse
import com.kalex.dogescollection.common.Constants
import com.kalex.dogescollection.dogList.model.data.dto.Dogs
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface DogsApi {

    @GET(Constants.GET_ALL_DOGS_URL)
    suspend fun getAllDogs() : Dogs
    @PUT(Constants.CREATE_ACCOUNT_URL)
    suspend fun createAccount(@Body signUpDTO: SignUpDTO): UserResponse

}