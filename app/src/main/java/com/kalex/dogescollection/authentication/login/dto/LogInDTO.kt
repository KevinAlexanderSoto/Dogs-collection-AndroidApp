package com.kalex.dogescollection.authentication.login.dto

import com.squareup.moshi.Json

data class LogInDTO(
    @field:Json(name = "email")
    val user: String,
    @field:Json(name = "password")
    val password: String,

)
