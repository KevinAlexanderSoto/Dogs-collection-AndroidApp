package com.kalex.dogescollection.authentication.model.dto

import com.kalex.dogescollection.dogList.model.data.dto.Data
import com.squareup.moshi.Json

data class User(
    val authenticationToken: String,
    val email: String,
    val id: Long
)
