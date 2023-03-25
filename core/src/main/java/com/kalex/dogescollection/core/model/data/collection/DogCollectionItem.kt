package com.kalex.dogescollection.core.model.data.collection

import com.squareup.moshi.Json

data class DogCollectionItem(
    @field:Json(name = "dog_id")
    val dogId: Long,
)
