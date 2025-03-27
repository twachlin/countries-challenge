package com.example.countries_challenge.domain.feature.countries.repository

import com.example.countries_challenge.domain.utils.UseCaseResult

interface CountriesRepository {
    suspend fun importCountriesFromRemote(): UseCaseResult<Unit>
}