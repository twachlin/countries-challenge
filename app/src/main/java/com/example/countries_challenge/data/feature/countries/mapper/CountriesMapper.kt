package com.example.countries_challenge.data.feature.countries.mapper

import com.example.countries_challenge.data.feature.countries.local.entity.CityEntity
import com.example.countries_challenge.data.feature.countries.model.CityApiModel
import com.example.countries_challenge.domain.feature.countries.model.CityLocationModel
import com.example.countries_challenge.domain.feature.countries.model.CityModel

fun CityApiModel.toCountryEntity(): CityEntity? {
    val id = this.id?.toString() ?: return null
    val country = this.country ?: return null
    val name = this.name ?: return null
    val coordinates = this.coordinates ?: return null
    val lon = coordinates.lon ?: return null
    val lat = coordinates.lat ?: return null

    return CityEntity(
        id = id,
        country = country,
        name = name,
        lon = lon,
        lat = lat
    )
}

/**
 * Maps a list of CountryEntity objects to a list of CountryModel objects.
 *
 * @param entities The list of CountryEntity objects to map.
 * @return A list of CountryModel objects.
 */
fun List<CityEntity>.toCountryModelList(): List<CityModel> {
    return this.map { entity ->
        CityModel(
            country = entity.country,
            name = entity.name,
            id = entity.id.toInt(),
            coordinates = CityLocationModel(
                lon = entity.lon,
                lat = entity.lat
            )
        )
    }
}