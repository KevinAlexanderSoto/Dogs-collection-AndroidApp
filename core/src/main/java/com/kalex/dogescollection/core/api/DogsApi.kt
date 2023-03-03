package com.kalex.dogescollection.core.api


import com.kalex.dogescollection.core.common.Constants
import com.kalex.dogescollection.core.model.data.alldogs.Dogs
import com.kalex.dogescollection.core.model.data.alldogs.SingleDog
import com.kalex.dogescollection.core.model.data.collection.AddDogCollectionResponse
import com.kalex.dogescollection.core.model.data.collection.DogCollectionItem
import com.kalex.dogescollection.core.model.dto.LogInDTO
import com.kalex.dogescollection.core.model.dto.SignUpDTO
import com.kalex.dogescollection.core.model.dto.UserResponse
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
    suspend fun getDogPredicted(@Query(Constants.GET_PREDICTED_QUERY) id : String) : SingleDog

}