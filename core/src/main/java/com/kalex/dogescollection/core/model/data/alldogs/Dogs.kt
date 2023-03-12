package com.kalex.dogescollection.core.model.data.alldogs

import com.squareup.moshi.Json

data class Dogs(
    @field:Json(name = "data")
    val body_data: Data,
    val is_success: Boolean,
    val message: String
)