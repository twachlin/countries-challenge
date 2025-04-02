package com.example.countries_challenge.domain.feature.countries.model

data class CitiesPagedModel(
    val cities: List<CityModel>,
    val isLastPage: Boolean,
)