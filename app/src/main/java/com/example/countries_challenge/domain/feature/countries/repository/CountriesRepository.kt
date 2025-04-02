package com.example.countries_challenge.domain.feature.countries.repository

import com.example.countries_challenge.domain.feature.countries.model.CitiesPagedModel
import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.domain.utils.UseCaseResult

interface CountriesRepository {
    suspend fun importCitiesIfNecessary(): UseCaseResult<Unit>

    suspend fun getCitiesPaged(
        page: Int,
        pageSize: Int,
        prefix: String?,
        showOnlyFavorites: Boolean,
    ): UseCaseResult<CitiesPagedModel>

    suspend fun getFavoriteCities(): UseCaseResult<List<CityModel>>

    suspend fun setCityAsFavorite(city: CityModel): UseCaseResult<Unit>

    suspend fun removeCityFromFavorites(city: CityModel): UseCaseResult<Unit>

}