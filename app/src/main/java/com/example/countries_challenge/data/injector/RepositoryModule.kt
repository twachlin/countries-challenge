package com.example.countries_challenge.data.injector

import com.example.countries_challenge.data.feature.countries.repository.CountriesRepositoryImpl
import com.example.countries_challenge.domain.feature.countries.repository.CountriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCountriesRepository(
        countriesRepositoryImpl: CountriesRepositoryImpl
    ): CountriesRepository

}