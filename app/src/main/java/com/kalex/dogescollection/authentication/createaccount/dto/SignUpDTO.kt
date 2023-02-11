package com.kalex.dogescollection.authentication.createaccount.dto

import com.squareup.moshi.Json

data class SignUpDTO(
    @field:Json(name = "email")
   val user: String,
   @field:Json(name = "password")
   val password: String,
   @field:Json(name = "password_confirmation")
   val passwordConfirm: String
)
