package com.kalex.dogescollection.dogList.model.data.alldogs

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dog(
    val created_at: String = "",
    val dog_type: String = "",
    val height_female: String = "",
    val height_male: String = "",
    val id: Int ,
    val image_url: String  = "",
    val index: Int,
    val life_expectancy: String = "",
    val ml_id: String = "",
    val name_en: String = "",
    val name_es: String = "",
    val temperament: String = "",
    val temperament_en: String = "",
    val updated_at: String = "",
    val weight_female: String = "",
    val weight_male: String = ""
):Parcelable,Comparable<Dog>{
    /**
     * To order the list of dogs
     * **/
    override fun compareTo(other: Dog) = if (this.index > other.index) 1 else -1

}