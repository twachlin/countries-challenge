package com.example.countries_challenge.data.feature.countries.net

import com.example.countries_challenge.data.feature.countries.model.CountryApiModel
import javax.inject.Inject

class CountriesDataSource @Inject constructor(
    private val api: CountriesApi
) {
    suspend fun getCountries(): List<CountryApiModel> {
        return api.getCountries()
    }
}