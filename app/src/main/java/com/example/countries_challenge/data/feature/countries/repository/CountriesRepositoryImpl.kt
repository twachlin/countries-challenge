package com.example.countries_challenge.data.feature.countries.repository

import com.example.countries_challenge.data.feature.countries.cache.CountriesCacheDataSource
import com.example.countries_challenge.data.feature.countries.local.CountriesLocalDataSource
import com.example.countries_challenge.data.feature.countries.mapper.toCityEntity
import com.example.countries_challenge.data.feature.countries.mapper.toCityModelList
import com.example.countries_challenge.data.feature.countries.model.CityApiModel
import com.example.countries_challenge.data.feature.countries.net.CountriesDataSource
import com.example.countries_challenge.data.util.net.BaseRepository
import com.example.countries_challenge.domain.feature.countries.model.CitiesPagedModel
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

    override suspend fun importCitiesIfNecessary(): UseCaseResult<Unit> {
        return safeApiCall {
            val citiesCount = countriesLocalDataSource.getCitiesCount()
            if (citiesCount == 0) {
                val cities = countriesDataSource.getCities()
                saveCitiesData(countries = cities)
            } else {
                val cityNames = countriesLocalDataSource.getAllCityNames()
                cityNames.chunked(COUNTRIES_BATCH_SIZE).forEach { names ->
                    names.forEach { name ->
                        saveCityNameForSearch(name = name)
                    }
                }
            }
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
            val entities = apiModels.mapNotNull { it.toCityEntity() }

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

    override suspend fun getCitiesPaged(
        page: Int,
        pageSize: Int,
        prefix: String?,
        showOnlyFavorites: Boolean,
    ): UseCaseResult<CitiesPagedModel> {
        return safeApiCall {
            val offset = (page - 1) * pageSize
            val cities = if (prefix != null) {
                countriesLocalDataSource.getCitiesByNamePaged(
                    cityNames = CountriesCacheDataSource.getCitiesByPrefix(prefix),
                    pageSize = pageSize,
                    offset = offset,
                    showOnlyFavorites = showOnlyFavorites
                )
            } else {
                countriesLocalDataSource.getCitiesPaged(
                    pageSize = pageSize,
                    offset = offset,
                    showOnlyFavorites = showOnlyFavorites
                )
            }
            val isLastPage = cities.size < pageSize
            cities.toCityModelList()
            CitiesPagedModel(
                cities = cities.toCityModelList(),
                isLastPage = isLastPage
            )
        }
    }

    override suspend fun getFavoriteCities(): UseCaseResult<List<CityModel>> {
        return safeApiCall {
            countriesLocalDataSource.getFavoriteCities().toCityModelList()
        }
    }

    override suspend fun setCityAsFavorite(city: CityModel): UseCaseResult<Unit> {
        return safeApiCall {
            val cityModel = city.copy(isFavourite = true)
            countriesLocalDataSource.updateCity(cityModel.toCityEntity())
        }
    }

    override suspend fun removeCityFromFavorites(city: CityModel): UseCaseResult<Unit> {
        return safeApiCall {
            val cityModel = city.copy(isFavourite = false)
            countriesLocalDataSource.updateCity(cityModel.toCityEntity())
        }
    }
}