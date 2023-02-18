package com.kalex.dogescollection.dogList.model.usecase

import com.kalex.dogescollection.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.common.networkstates.makeNetworkCallHandler
import com.kalex.dogescollection.dogList.model.data.alldogs.Data
import com.kalex.dogescollection.dogList.model.data.alldogs.Dog
import com.kalex.dogescollection.dogList.model.data.collection.AddDogCollectionResponse
import com.kalex.dogescollection.dogList.model.repository.DogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogsUseCase @Inject constructor(
    private val dogRepository: DogRepository
) {
     fun getAllDogs() : Flow<UseCaseFlowStatus<Data>> = makeNetworkCallHandler{
         val result = dogRepository.getDogs()
         if(result.is_success){
             result.body_data
         }else{
             throw Exception(result.message)
         }
     }

    fun addDogToCollection(dogId : Long) :Flow<UseCaseFlowStatus<Boolean>> = makeNetworkCallHandler {
        val result = dogRepository.addDogToCollection(dogId)
        if (result.is_success) return@makeNetworkCallHandler true
        throw java.lang.Exception(result.message)
    }

   suspend  fun getDogCollection(): Flow<UseCaseFlowStatus<List<Dog>>> = makeNetworkCallHandler{
        coroutineScope{
            val allDogsList = async(Dispatchers.IO) {
                dogRepository.getDogs()
            }
            val userDogsList = async(Dispatchers.IO) {
                dogRepository.getDogCollection()
            }
            if(allDogsList.await().is_success && userDogsList.await().is_success){
                allDogsList.await().body_data.dogs.map {
                    if (  userDogsList.await().body_data.dogs.contains(it)){
                        it
                    }else{
                        Dog(index = it.index, id = it.id)
                    }
                }.sorted()
            }else{
                throw Exception("dog_collection_error")
            }
        }
    }
}





