package com.kalex.dogescollection.core.model.dto

data class User(
    val authenticationToken: String,
    val email: String,
    val id: Long
)
