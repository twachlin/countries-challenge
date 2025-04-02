package com.example.countries_challenge.domain.feature.countries.usecase

import com.example.countries_challenge.domain.base.BaseUseCase
import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.domain.feature.countries.repository.CountriesRepository
import com.example.countries_challenge.domain.feature.countries.usecase.SetCityAsFavoriteUseCase.Params
import com.example.countries_challenge.domain.utils.UseCaseResult
import javax.inject.Inject

class SetCityAsFavoriteUseCase @Inject constructor(
    val repository: CountriesRepository
) : BaseUseCase<Unit, Params>() {

    override suspend fun buildUseCase(params: Params): UseCaseResult<Unit> {
        return repository.setCityAsFavorite(params.city)
    }

    data class Params(
        val city: CityModel
    )
}