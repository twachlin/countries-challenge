package com.example.countries_challenge.data.feature.countries.mapper

import com.example.countries_challenge.data.feature.countries.local.entity.CountryEntity
import com.example.countries_challenge.data.feature.countries.model.CountryApiModel

fun CountryApiModel.toCountryEntity(): CountryEntity? {
    val id = this.id?.toString() ?: return null
    val country = this.country ?: return null
    val name = this.name ?: return null
    val coordinates = this.coordinates ?: return null
    val lon = coordinates.lon ?: return null
    val lat = coordinates.lat ?: return null

    return CountryEntity(
        id = id,
        country = country,
        name = name,
        lon = lon,
        lat = lat
    )
}