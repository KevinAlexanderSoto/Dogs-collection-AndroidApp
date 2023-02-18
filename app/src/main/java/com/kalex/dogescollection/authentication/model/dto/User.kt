package com.kalex.dogescollection.authentication.model.dto

data class User(
    val authenticationToken: String,
    val email: String,
    val id: Long
)
