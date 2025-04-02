package com.example.countries_challenge.data.feature.countries.mapper

import com.example.countries_challenge.data.feature.countries.local.entity.CityEntity
import com.example.countries_challenge.data.feature.countries.model.CityApiModel
import com.example.countries_challenge.domain.feature.countries.model.CityLocationModel
import com.example.countries_challenge.domain.feature.countries.model.CityModel

fun CityApiModel.toCityEntity(): CityEntity? {
    val id = this.id ?: return null
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
 * Maps a list of CityEntity objects to a list of CityModel objects.
 *
 * @return A list of CityModel objects.
 */
fun List<CityEntity>.toCityModelList(): List<CityModel> {
    return this.map { entity ->
        CityModel(
            country = entity.country,
            name = entity.name,
            id = entity.id,
            isFavourite = entity.isFavourite,
            coordinates = CityLocationModel(
                lon = entity.lon,
                lat = entity.lat
            ),
        )
    }
}

fun CityModel.toCityEntity(): CityEntity {
    return CityEntity(
        id = id,
        country = country,
        name = name,
        lon = coordinates.lon,
        lat = coordinates.lat,
        isFavourite = isFavourite
    )
}