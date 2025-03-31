package com.example.countries_challenge.data.feature.countries.repository

import com.example.countries_challenge.data.feature.countries.cache.CountriesCacheDataSource
import com.example.countries_challenge.data.feature.countries.local.CountriesLocalDataSource
import com.example.countries_challenge.data.feature.countries.mapper.toCountryEntity
import com.example.countries_challenge.data.feature.countries.mapper.toCountryModelList
import com.example.countries_challenge.data.feature.countries.model.CityApiModel
import com.example.countries_challenge.data.feature.countries.net.CountriesDataSource
import com.example.countries_challenge.data.util.net.BaseRepository
import com.example.countries_challenge.domain.feature.countries.model.CityModel
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
            val cities = countriesDataSource.getCities()
            saveCitiesData(countries = cities)
        }
    }

    /**
     * Saves country data to the local database and populates the Trie data structure for efficient searching.
     * This function prioritizes data availability and search speed over immediate application startup time.
     */
    private fun saveCitiesData(
        countries: List<CityApiModel>,
    ) {
        countries.chunked(COUNTRIES_BATCH_SIZE).forEach { apiModels ->
            val entities = apiModels.mapNotNull { it.toCountryEntity() }

            // Populate the Trie with city names for fast prefix-based searching.
            entities.forEach { entity ->
                saveCityNameForSearch(entity.name)
            }

            // Persist the converted country entities to the local database.
            if (entities.isNotEmpty()) {
                countriesLocalDataSource.saveCities(entities)
            }
        }
    }

    /**
     * Adds a city name to the Trie data structure to enable efficient prefix-based searching.
     *
     * @param name The name of the city to be added to the Trie.
     */
    private fun saveCityNameForSearch(name: String) {
        CountriesCacheDataSource.addCityNameToTrie(name)
    }

    suspend fun getCitiesByNamePaged(
        cityNames: List<String>,
        page: Int,
        pageSize: Int
    ): UseCaseResult<List<CityModel>> {
        return safeApiCall {
            val offset = (page - 1) * pageSize
            countriesLocalDataSource.getCitiesByNamePaged(
                cityNames = cityNames,
                pageSize = pageSize,
                offset = offset
            ).toCountryModelList()
        }
    }

    override suspend fun getCitiesByPrefix(
        prefix: String,
        page: Int,
        pageSize: Int
    ): UseCaseResult<List<CityModel>> {
        val citiesNames = CountriesCacheDataSource.getCitiesByPrefix(prefix)
        return getCitiesByNamePaged(
            cityNames = citiesNames,
            page = page,
            pageSize = pageSize
        )
    }

    override suspend fun getCitiesPaged(
        page: Int,
        pageSize: Int
    ): UseCaseResult<List<CityModel>> {
        return safeApiCall {
            val cities = countriesLocalDataSource.getCitiesPaged(
                pageSize = pageSize,
                offset = (page - 1) * pageSize
            )
            cities.toCountryModelList()
        }
    }

}