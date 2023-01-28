package com.kalex.dogescollection.dogList.model.usecase

import com.kalex.dogescollection.dogList.model.data.dto.Data
import com.kalex.dogescollection.dogList.model.data.dto.Dog
import com.kalex.dogescollection.dogList.model.data.dto.Dogs
import com.kalex.dogescollection.dogList.model.repository.DogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DogsUseCase @Inject constructor(
    private val dogRepository: DogRepository
) {

     fun getAllDogs() : Flow<DogsFlowStatus> = flow {
         val result = dogRepository.getDogs()
         when(result.is_success){
             true -> emit(DogsFlowStatus.Success(result.body_data))
             false -> emit(DogsFlowStatus.Error(result.message))
         }
     }


}

sealed class DogsFlowStatus{
    data class Success(val dogsList :Data) : DogsFlowStatus()
    data class Error(val exception: String ) : DogsFlowStatus()
}