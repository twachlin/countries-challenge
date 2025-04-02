package com.example.countries_challenge.data.feature.countries.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val id: Int,
    val country: String,
    val name: String,
    val lon: Double,
    val lat: Double,
    val isFavourite: Boolean = false,
)