package com.example.countries_challenge.domain.feature.countries.model

data class CityModel (
    val country: String,
    val name: String,
    val id: Int,
    val isFavourite: Boolean,
    val coordinates: CityLocationModel
)

data class CityLocationModel(
    val lon: Double,
    val lat: Double
)