package com.example.countries_challenge.domain.feature.countries.usecase

import com.example.countries_challenge.domain.base.BaseUseCase
import com.example.countries_challenge.domain.feature.countries.repository.CountriesRepository
import com.example.countries_challenge.domain.utils.UseCaseResult
import javax.inject.Inject

class ImportCountriesUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository,
) : BaseUseCase<Unit, Unit>() {

    override suspend fun buildUseCase(params: Unit): UseCaseResult<Unit> {
        return countriesRepository.importCountriesFromRemote()
    }
}