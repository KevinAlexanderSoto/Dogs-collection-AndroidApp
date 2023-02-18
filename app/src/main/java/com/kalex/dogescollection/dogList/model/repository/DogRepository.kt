package com.kalex.dogescollection.dogList.model.repository

import com.kalex.dogescollection.dogList.model.data.alldogs.Dogs
import com.kalex.dogescollection.dogList.model.data.collection.AddDogCollectionResponse

interface DogRepository {
    /**
     * Get all the dogs in the DB
     * **/
    suspend fun getDogs(): Dogs
    suspend fun addDogToCollection(dogId : Long): AddDogCollectionResponse
    /**
     * Get the dog by user, if it is added to the use collection
     * **/
    suspend fun getDogCollection(): Dogs
}