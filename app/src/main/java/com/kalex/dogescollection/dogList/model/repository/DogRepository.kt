package com.kalex.dogescollection.dogList.model.repository

import com.kalex.dogescollection.dogList.model.data.alldogs.Dogs

interface DogRepository {
    suspend fun getDogs(): Dogs
}