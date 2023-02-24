package com.kalex.dogescollection.dogList.model.data.alldogs

import com.squareup.moshi.Json

data class SingleDog(
    @field:Json(name = "data")
    val body_data: SingleData,
    val is_success: Boolean,
    val message: String
)
