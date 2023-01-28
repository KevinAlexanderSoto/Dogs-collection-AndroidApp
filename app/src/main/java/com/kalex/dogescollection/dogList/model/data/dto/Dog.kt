package com.kalex.dogescollection.dogList.model.data.dto

data class Dog(
    val created_at: String,
    val dog_type: String,
    val height_female: String,
    val height_male: String,
    val id: Int,
    val image_url: String,
    val index: Int,
    val life_expectancy: String,
    val ml_id: String,
    val name_en: String,
    val name_es: String,
    val temperament: String,
    val temperament_en: String,
    val updated_at: String,
    val weight_female: String,
    val weight_male: String
)