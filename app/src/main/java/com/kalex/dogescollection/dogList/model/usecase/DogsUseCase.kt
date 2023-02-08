package com.kalex.dogescollection.dogList.model.usecase

import com.kalex.dogescollection.common.networkstates.UseCaseFlowStatus
import com.kalex.dogescollection.common.networkstates.makeNetworkCallHandler
import com.kalex.dogescollection.dogList.model.data.dto.Data
import com.kalex.dogescollection.dogList.model.data.dto.Dog
import com.kalex.dogescollection.dogList.model.data.dto.Dogs
import com.kalex.dogescollection.dogList.model.repository.DogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
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
}





