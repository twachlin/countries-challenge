package com.example.countries_challenge.data.feature.countries.net

import com.example.countries_challenge.data.feature.countries.model.CountryApiModel
import retrofit2.http.GET

interface CountriesApi {
    @GET("dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json")
    suspend fun getCountries(): List<CountryApiModel>
}