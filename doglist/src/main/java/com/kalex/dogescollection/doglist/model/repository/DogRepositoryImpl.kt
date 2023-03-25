package com.kalex.dogescollection.doglist.model.repository

import com.kalex.dogescollection.core.api.DogsApi
import com.kalex.dogescollection.core.model.data.alldogs.Dogs
import com.kalex.dogescollection.core.model.data.alldogs.SingleDog
import com.kalex.dogescollection.core.model.data.collection.AddDogCollectionResponse
import com.kalex.dogescollection.core.model.data.collection.DogCollectionItem
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val dogApi: DogsApi,
) : DogRepository {
    override suspend fun getDogs(): Dogs {
        return dogApi.getAllDogs()
    }

    override suspend fun addDogToCollection(dogId: Long): AddDogCollectionResponse {
        return dogApi.addDogToCollection(
            DogCollectionItem(
                dogId,
            ),
        )
    }
    override suspend fun getDogCollection(): Dogs {
        return dogApi.getDogCollection()
    }

    override suspend fun getDogByPredictedId(predictedId: String): SingleDog {
        return dogApi.getDogPredicted(predictedId)
    }
}
