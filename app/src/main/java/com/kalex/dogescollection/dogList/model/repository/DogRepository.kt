package com.kalex.dogescollection.dogList.model.repository

import com.kalex.dogescollection.dogList.model.data.dto.Dogs

interface DogRepository {
    suspend fun getDogs(): Dogs
}