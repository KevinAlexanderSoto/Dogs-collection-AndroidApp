package com.kalex.dogescollection.dogList.model.data.collection

import com.squareup.moshi.Json

data class DogCollectionItem (
    @field:Json(name = "dog_id")
    val dogId : String
        )