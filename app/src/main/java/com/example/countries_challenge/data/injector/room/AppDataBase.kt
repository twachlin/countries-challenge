package com.example.countries_challenge.data.injector.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.countries_challenge.data.feature.countries.local.CountryDao
import com.example.countries_challenge.data.feature.countries.local.entity.CountryEntity

@Database(
    entities = [
        CountryEntity::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase: RoomDatabase() {
    abstract fun getCountryDao(): CountryDao
}