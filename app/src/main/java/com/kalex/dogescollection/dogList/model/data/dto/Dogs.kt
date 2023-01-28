package com.kalex.dogescollection.dogList.model.data.dto

import com.squareup.moshi.Json

data class Dogs(
    @Json(name = "data")
    val body_data: Data,
    val is_success: Boolean,
    val message: String
)