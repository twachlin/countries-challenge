package com.example.countries_challenge.domain.feature.countries.usecase

import com.example.countries_challenge.domain.base.BaseUseCase
import com.example.countries_challenge.domain.feature.countries.model.CitiesPagedModel
import com.example.countries_challenge.domain.feature.countries.repository.CountriesRepository
import com.example.countries_challenge.domain.utils.UseCaseResult
import javax.inject.Inject

class GetCitiesPagedUseCase @Inject constructor(
    private val repository: CountriesRepository,
) : BaseUseCase<CitiesPagedModel, GetCitiesPagedUseCase.Params>() {

    private companion object {
        const val PAGE_SIZE = 50
    }

    override suspend fun buildUseCase(params: Params): UseCaseResult<CitiesPagedModel> {
        return repository.getCitiesPaged(
            pageSize = PAGE_SIZE,
            page = params.page,
            prefix = params.prefix,
            showOnlyFavorites = params.showOnlyFavorites
        )
    }


    data class Params(
        val page: Int,
        val prefix: String?,
        val showOnlyFavorites: Boolean = false,
    )
}