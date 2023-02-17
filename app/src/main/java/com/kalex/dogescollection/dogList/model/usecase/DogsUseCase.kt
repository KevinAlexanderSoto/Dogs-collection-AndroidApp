package com.kalex.dogescollection.dogList.model.usecase

import com.kalex.dogescollection.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.common.networkstates.makeNetworkCallHandler
import com.kalex.dogescollection.dogList.model.data.alldogs.Data
import com.kalex.dogescollection.dogList.model.data.collection.AddDogCollectionResponse
import com.kalex.dogescollection.dogList.model.repository.DogRepository
import kotlinx.coroutines.flow.Flow
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

    fun addDogToCollection(dogId : String) :Flow<UseCaseFlowStatus<Boolean>> = makeNetworkCallHandler {
        val result = dogRepository.addDogToCollection(dogId)
        if (result.is_success) return@makeNetworkCallHandler true
        throw java.lang.Exception(result.message)
    }
}





