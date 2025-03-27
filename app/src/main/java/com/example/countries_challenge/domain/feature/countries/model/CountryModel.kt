package com.example.countries_challenge.domain.feature.countries.model

data class CountryModel (
    val country: String,
    val name: String,
    val id: Int,
    val coordinates: CountryLocationModel
)

data class CountryLocationModel(
    val lon: Double,
    val lat: Double
)