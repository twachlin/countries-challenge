package com.example.countries_challenge.data.feature.countries.local

import androidx.room.Transaction
import com.example.countries_challenge.data.feature.countries.local.entity.CityEntity
import javax.inject.Inject

class CountriesLocalDataSource @Inject constructor(
    private val countryDao: CountryDao
) {
    fun getAllCityNames(): List<String> {
        return countryDao.getAllCityNames()
    }

    @Transaction
    fun saveCities(countries: List<CityEntity>) {
        countryDao.insertAllCities(countries)
    }

    fun getCitiesByNamePaged(
        cityNames: List<String>,
        pageSize: Int,
        offset: Int,
        showOnlyFavorites: Boolean,
    ): List<CityEntity> {
        return countryDao.getCitiesByNamePaged(
            cityNames = cityNames,
            pageSize = pageSize,
            offset = offset,
            showOnlyFavorites = showOnlyFavorites
        )
    }

    fun getCitiesPaged(pageSize: Int, offset: Int, showOnlyFavorites: Boolean): List<CityEntity> {
        return countryDao.getCitiesPaged(
            pageSize = pageSize,
            offset = offset,
            showOnlyFavorites = showOnlyFavorites
        )
    }

    fun getFavoriteCities(): List<CityEntity> {
        return countryDao.getFavoriteCities()
    }

    fun updateCity(city: CityEntity) {
        countryDao.updateCity(city)
    }

    fun getCitiesCount(): Int {
        return countryDao.getCitiesCount()
    }
}