package com.example.countries_challenge.data.feature.countries.local

import androidx.room.Transaction
import com.example.countries_challenge.data.feature.countries.local.entity.CityEntity
import javax.inject.Inject

class CountriesLocalDataSource @Inject constructor(
    private val countryDao: CountryDao
) {
    @Transaction
    fun saveCities(countries: List<CityEntity>) {
        countryDao.insertAllCities(countries)
    }

    fun getCitiesByNamePaged(
        cityNames: List<String>,
        pageSize: Int,
        offset: Int
    ): List<CityEntity> {
        return countryDao.getCitiesByNamePaged(cityNames, pageSize, offset)
    }

    fun getCitiesPaged(pageSize: Int, offset: Int): List<CityEntity> {
        return countryDao.getCitiesPaged(pageSize, offset)
    }
}