package com.kalex.dogescollection.dogList.model.usecase

import com.kalex.dogescollection.core.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.core.common.networkstates.makeNetworkCallHandler
import com.kalex.dogescollection.core.model.data.alldogs.Data
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import com.kalex.dogescollection.dogList.model.repository.DogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DogsUseCase @Inject constructor(
    private val dogRepository: DogRepository
) {
    fun getAllDogs(): Flow<UseCaseFlowStatus<Data>> =
        makeNetworkCallHandler {
            val result = dogRepository.getDogs()
            if (result.is_success) return@makeNetworkCallHandler result.body_data
            throw Exception(result.message)
        }

    fun addDogToCollection(dogId: Long): Flow<UseCaseFlowStatus<Boolean>> =
       makeNetworkCallHandler {
            val result = dogRepository.addDogToCollection(dogId)
            if (result.is_success) return@makeNetworkCallHandler true
            throw java.lang.Exception(result.message)
        }

    suspend fun getDogCollection(): Flow<UseCaseFlowStatus<List<Dog>>> =
        makeNetworkCallHandler {
            coroutineScope {
                val allDogs = async(Dispatchers.IO) {
                    dogRepository.getDogs()
                }
                val userDogs = async(Dispatchers.IO) {
                    dogRepository.getDogCollection()
                }
                val allDogsList = allDogs.await()
                val userDogsList = userDogs.await()

                if (allDogsList.is_success && userDogsList.is_success) {
                    val listSorted = async(Dispatchers.Default) {
                        allDogsList.body_data.dogs.map {
                            if (userDogsList.body_data.dogs.contains(it)) {
                                it.inCollection = true
                                it
                            } else {
                                Dog(
                                    index = it.index,
                                    id = it.id,
                                    inCollection = false,
                                    name_es = it.name_es
                                )
                            }
                        }.sorted()
                    }
                    return@coroutineScope listSorted.await()

                } else {
                    throw Exception("dog_collection_error")
                }
            }
        }

    suspend fun getDogByPredictedId(predictedId : String): Flow<UseCaseFlowStatus<Dog>> =
        makeNetworkCallHandler {
            val result = dogRepository.getDogByPredictedId(predictedId)
            if (result.is_success) return@makeNetworkCallHandler result.body_data.dog
            throw java.lang.Exception(result.message)
        }
}





