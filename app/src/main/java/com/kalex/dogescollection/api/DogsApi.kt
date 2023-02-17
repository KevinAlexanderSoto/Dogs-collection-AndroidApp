package com.kalex.dogescollection.api

import com.kalex.dogescollection.authentication.createaccount.dto.SignUpDTO
import com.kalex.dogescollection.authentication.model.dto.UserResponse
import com.kalex.dogescollection.authentication.login.dto.LogInDTO
import com.kalex.dogescollection.common.Constants
import com.kalex.dogescollection.dogList.model.data.collection.AddDogCollectionResponse
import com.kalex.dogescollection.dogList.model.data.collection.DogCollectionItem
import com.kalex.dogescollection.dogList.model.data.alldogs.Dogs
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface DogsApi {

    @GET(Constants.GET_ALL_DOGS_URL)
    suspend fun getAllDogs() : Dogs
    @POST(Constants.CREATE_ACCOUNT_URL)
    suspend fun createAccount(@Body signUpDTO: SignUpDTO): UserResponse
    @POST(Constants.LOGIN_URL)
    suspend fun logIn(@Body signInDTO: LogInDTO): UserResponse

    @Headers("${ApiServiceInterceptor.AUTH_HEADER_KEY} : true")
    @POST(Constants.ADD_DOG_TO_USER_URL)
    suspend fun addDogToCollection(@Body dogCollectionItem: DogCollectionItem): AddDogCollectionResponse

}