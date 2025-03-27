package com.example.countries_challenge.data.feature.countries.model

import com.google.gson.annotations.SerializedName

data class CountryApiModel(
    @SerializedName("country")
    val country: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("_id")
    val id: Int?,
    @SerializedName("coord")
    val coordinates: CountryLocationApiModel?
)

data class CountryLocationApiModel(
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("lat")
    val lat: Double?
)