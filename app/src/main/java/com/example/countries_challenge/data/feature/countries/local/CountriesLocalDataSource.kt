package com.example.countries_challenge.data.feature.countries.local

import androidx.room.Transaction
import com.example.countries_challenge.data.feature.countries.local.entity.CountryEntity
import javax.inject.Inject

class CountriesLocalDataSource @Inject constructor(
    private val countryDao: CountryDao
) {
    @Transaction
    fun saveCountries(countries: List<CountryEntity>) {
        countryDao.insertAllCountries(countries)
    }
}