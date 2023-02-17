package com.kalex.dogescollection.dogList.model.repository

import com.kalex.dogescollection.dogList.model.data.alldogs.Dogs
import com.kalex.dogescollection.dogList.model.data.collection.AddDogCollectionResponse

interface DogRepository {
    suspend fun getDogs(): Dogs
    suspend fun addDogToCollection(dogId : String): AddDogCollectionResponse
}