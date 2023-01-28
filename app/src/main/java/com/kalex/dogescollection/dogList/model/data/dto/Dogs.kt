package com.kalex.dogescollection.dogList.model.data.dto

import com.squareup.moshi.Json
import retrofit2.http.Field

data class Dogs(
    @field:Json(name = "data")
    val body_data: Data,
    val is_success: Boolean,
    val message: String
)