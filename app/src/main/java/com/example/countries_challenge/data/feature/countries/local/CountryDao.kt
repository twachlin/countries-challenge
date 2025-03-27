package com.example.countries_challenge.data.feature.countries.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.countries_challenge.data.feature.countries.local.entity.CountryEntity

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCountries(countries: List<CountryEntity>)
}