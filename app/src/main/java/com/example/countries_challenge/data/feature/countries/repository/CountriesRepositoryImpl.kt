package com.example.countries_challenge.data.feature.countries.repository

import com.example.countries_challenge.data.feature.countries.local.CountriesLocalDataSource
import com.example.countries_challenge.data.feature.countries.mapper.toCountryEntity
import com.example.countries_challenge.data.feature.countries.model.CountryApiModel
import com.example.countries_challenge.data.feature.countries.net.CountriesDataSource
import com.example.countries_challenge.data.util.net.BaseRepository
import com.example.countries_challenge.domain.feature.countries.repository.CountriesRepository
import com.example.countries_challenge.domain.utils.UseCaseResult
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val countriesDataSource: CountriesDataSource,
    private val countriesLocalDataSource: CountriesLocalDataSource,
) : BaseRepository(), CountriesRepository {

    companion object {
        private const val COUNTRIES_BATCH_SIZE = 500
    }

    override suspend fun importCountriesFromRemote(): UseCaseResult<Unit> {
        return safeApiCall {
            val countries = countriesDataSource.getCountries()
            saveCountries(countries = countries)
        }
    }

    private fun saveCountries(
        countries: List<CountryApiModel>,
    ) {
        countries.chunked(COUNTRIES_BATCH_SIZE).forEach { apiModels ->
            val entities = apiModels.mapNotNull { it.toCountryEntity() }
            if (entities.isNotEmpty()) {
                countriesLocalDataSource.saveCountries(entities)
            }
        }
    }
}