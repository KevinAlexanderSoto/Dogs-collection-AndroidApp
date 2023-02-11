package com.kalex.dogescollection.authentication.createaccount.dto

import com.kalex.dogescollection.dogList.model.data.dto.Data
import com.squareup.moshi.Json

data class User(
    @field:Json(name = "authentication_token")
    val authenticationToken: String,
    val email: String,
    val id: Long
)
