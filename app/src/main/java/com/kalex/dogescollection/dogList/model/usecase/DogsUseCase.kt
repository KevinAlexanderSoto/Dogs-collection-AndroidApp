package com.kalex.dogescollection.dogList.model.usecase

import com.kalex.dogescollection.dogList.model.data.dto.Dogs
import com.kalex.dogescollection.dogList.model.repository.DogRepository
import javax.inject.Inject

class DogsUseCase @Inject constructor(
    private val dogRepository: DogRepository
) {
    suspend fun getAllDogs() : Dogs {
        return dogRepository.getDogs()
    }
}