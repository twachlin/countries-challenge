package com.example.countries_challenge.data.feature.countries.cache

import com.example.countries_challenge.data.feature.countries.model.Trie

object CountriesCacheDataSource {

    private val citiesTrie = Trie()

    fun addCityNameToTrie(cityName: String) {
        citiesTrie.insert(cityName)
    }

    fun getCitiesByPrefix(prefix: String): List<String> {
        return citiesTrie.startsWith(prefix)
    }
}