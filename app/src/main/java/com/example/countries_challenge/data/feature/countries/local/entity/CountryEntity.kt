package com.example.countries_challenge.data.feature.countries.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey val id: String,
    val country: String,
    val name: String,
    val lon: Double,
    val lat: Double
)