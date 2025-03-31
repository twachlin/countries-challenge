package com.example.countries_challenge.domain.feature.countries.repository

import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.domain.utils.UseCaseResult

interface CountriesRepository {
    suspend fun importCountriesFromRemote(): UseCaseResult<Unit>

    suspend fun getCitiesByPrefix(
        prefix: String,
        page: Int,
        pageSize: Int
    ): UseCaseResult<List<CityModel>>

    suspend fun getCitiesPaged(
        page: Int,
        pageSize: Int
    ): UseCaseResult<List<CityModel>>
}