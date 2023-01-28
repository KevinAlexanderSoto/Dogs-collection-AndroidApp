package com.kalex.dogescollection.dogList.model.data

import com.kalex.dogescollection.common.Constants
import com.kalex.dogescollection.dogList.model.data.dto.Dogs
import retrofit2.http.GET

interface DogsApi {

    @GET(Constants.GET_ALL_DOGS_URL)
    suspend fun getAllDogs() : Dogs

}