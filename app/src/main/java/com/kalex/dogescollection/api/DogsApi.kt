package com.kalex.dogescollection.api

import com.kalex.dogescollection.authentication.createaccount.dto.SignUpDTO
import com.kalex.dogescollection.authentication.model.dto.UserResponse
import com.kalex.dogescollection.authentication.login.dto.LogInDTO
import com.kalex.dogescollection.common.Constants
import com.kalex.dogescollection.dogList.model.data.collection.AddDogCollectionResponse
import com.kalex.dogescollection.dogList.model.data.collection.DogCollectionItem
import com.kalex.dogescollection.dogList.model.data.alldogs.Dogs
import com.kalex.dogescollection.dogList.model.data.alldogs.SingleDog
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface DogsApi {

    @GET(Constants.GET_ALL_DOGS_URL)
    suspend fun getAllDogs() : Dogs
    @POST(Constants.CREATE_ACCOUNT_URL)
    suspend fun createAccount(@Body signUpDTO: SignUpDTO): UserResponse
    @POST(Constants.LOGIN_URL)
    suspend fun logIn(@Body signInDTO: LogInDTO): UserResponse

    @Headers("${ApiServiceInterceptor.AUTH_HEADER_KEY}:true")
    @POST(Constants.ADD_DOG_TO_USER_URL)
    suspend fun addDogToCollection(@Body dogCollectionItem: DogCollectionItem): AddDogCollectionResponse

    @Headers("${ApiServiceInterceptor.AUTH_HEADER_KEY}:true")
    @GET(Constants.GET_USER_DOGS_URL)
    suspend fun getDogCollection(): Dogs

    @GET(Constants.GET_DOG_BY_ML_ID)
    suspend fun getDogPredicted(@Query("ml_id") id : String) : SingleDog

}