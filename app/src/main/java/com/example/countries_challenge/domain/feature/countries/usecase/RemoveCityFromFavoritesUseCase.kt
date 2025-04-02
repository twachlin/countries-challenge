package com.example.countries_challenge.domain.feature.countries.usecase

import com.example.countries_challenge.domain.base.BaseUseCase
import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.domain.feature.countries.repository.CountriesRepository
import com.example.countries_challenge.domain.utils.UseCaseResult
import javax.inject.Inject

class RemoveCityFromFavoritesUseCase @Inject constructor(
    private val repository: CountriesRepository,
) : BaseUseCase<Unit, RemoveCityFromFavoritesUseCase.Params>() {

    override suspend fun buildUseCase(params: Params): UseCaseResult<Unit> {
        return repository.removeCityFromFavorites(params.city)
    }

    data class Params(
        val city: CityModel,
    )
}