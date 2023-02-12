package com.kalex.dogescollection.authentication.createaccount.dto

import com.kalex.dogescollection.dogList.model.data.dto.Data
import com.squareup.moshi.Json

data class UserResponse(
    @field:Json(name = "data")
    val body_data: UserDTO,
    val is_success: Boolean,
    val message: String
)
