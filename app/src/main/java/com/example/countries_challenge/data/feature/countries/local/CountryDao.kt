package com.example.countries_challenge.data.feature.countries.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.countries_challenge.data.feature.countries.local.entity.CityEntity

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCities(countries: List<CityEntity>)

    @Query(
        """
         SELECT * FROM cities
         WHERE LOWER(name) IN (:cityNames) 
         ORDER BY name 
         LIMIT :pageSize OFFSET :offset
         """
    )
    fun getCitiesByNamePaged(
        cityNames: List<String>,
        pageSize: Int,
        offset: Int,
    ): List<CityEntity>
}