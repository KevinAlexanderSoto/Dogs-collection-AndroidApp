package com.kalex.dogescollection.core.model.dto

import com.squareup.moshi.Json

data class LogInDTO(
    @field:Json(name = "email")
    val user: String,
    @field:Json(name = "password")
    val password: String,

)
