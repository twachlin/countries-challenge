package com.example.countries_challenge.data.injector

import com.example.countries_challenge.data.feature.countries.net.CountriesApi
import com.example.countries_challenge.data.feature.countries.net.CountriesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideCountriesDataSource(api: CountriesApi): CountriesDataSource {
        return CountriesDataSource(api)
    }
}