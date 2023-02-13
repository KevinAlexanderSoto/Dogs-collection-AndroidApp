package com.kalex.dogescollection.authentication.model.dto

import com.squareup.moshi.Json

data class UserResponse(
    @field:Json(name = "data")
    val body_data: UserAccountData,
    val is_success: Boolean,
    val message: String
)
