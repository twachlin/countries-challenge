package com.example.countries_challenge.data.feature.countries.net

import com.example.countries_challenge.data.feature.countries.model.CityApiModel
import javax.inject.Inject

class CountriesDataSource @Inject constructor(
    private val api: CountriesApi
) {
    suspend fun getCities(): List<CityApiModel> {
        return api.getCities()
    }
}