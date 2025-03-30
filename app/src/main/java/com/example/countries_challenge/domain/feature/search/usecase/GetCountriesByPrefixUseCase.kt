package com.example.countries_challenge.domain.feature.search.usecase

import com.example.countries_challenge.domain.base.BaseUseCase
import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.domain.feature.countries.repository.CountriesRepository
import com.example.countries_challenge.domain.feature.search.usecase.GetCountriesByPrefixUseCase.*
import com.example.countries_challenge.domain.utils.UseCaseResult
import javax.inject.Inject

class GetCountriesByPrefixUseCase @Inject constructor(
    private val repository: CountriesRepository,
) : BaseUseCase<List<CityModel>, Params>() {

    private companion object {
        const val PAGE_SIZE = 25
    }

    override suspend fun buildUseCase(params: Params): UseCaseResult<List<CityModel>> {
        return repository.getCitiesByPrefix(
            prefix = params.prefix,
            page = params.page,
            pageSize = PAGE_SIZE
        )
    }

    data class Params(
        val prefix: String,
        val page: Int,
    )
}