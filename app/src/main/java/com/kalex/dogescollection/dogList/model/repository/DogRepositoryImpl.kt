package com.kalex.dogescollection.dogList.model.repository

import com.kalex.dogescollection.api.DogsApi
import com.kalex.dogescollection.dogList.model.data.alldogs.Dogs
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor (
    private val dogApi : DogsApi
        ):DogRepository {
    override suspend fun getDogs(): Dogs {
        return dogApi.getAllDogs()
    }
}