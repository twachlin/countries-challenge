package com.example.countries_challenge.domain.feature.countries.usecase

import com.example.countries_challenge.domain.base.BaseUseCase
import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.domain.feature.countries.repository.CountriesRepository
import com.example.countries_challenge.domain.utils.UseCaseResult
import javax.inject.Inject

class GetCitiesPagedUseCase @Inject constructor(
    private val repository: CountriesRepository,
) : BaseUseCase<List<CityModel>, GetCitiesPagedUseCase.Params>() {

    private companion object {
        const val PAGE_SIZE = 50
    }

    override suspend fun buildUseCase(params: Params): UseCaseResult<List<CityModel>> {
        return repository.getCitiesPaged(pageSize = PAGE_SIZE, page = params.page)
    }


    data class Params(
        val page: Int,
    )
}