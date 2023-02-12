package com.kalex.dogescollection.authentication.createaccount.dto

data class UserDTO(
    val authentication_token: String,
    val created_at: String,
    val email: String,
    val id: Int,
    val updated_at: String
)

fun UserDTO.userDTOtoUser() = User(
    authenticationToken = this.authentication_token,
    email = this.email,
    id = this.id.toLong()
)