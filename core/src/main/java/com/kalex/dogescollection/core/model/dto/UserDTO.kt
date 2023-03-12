package com.kalex.dogescollection.core.model.dto

import com.squareup.moshi.Json

data class UserDTO(
    @field:Json(name = "authentication_token")
    val authentication_token: String,
    @field:Json(name = "created_at")
    val created_at: String,
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "updated_at")
    val updated_at: String
)

fun UserDTO.userDTOtoUser() = User(
    authenticationToken = this.authentication_token,
    email = this.email,
    id = this.id.toLong()
)