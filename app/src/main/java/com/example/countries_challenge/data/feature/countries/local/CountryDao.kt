package com.example.countries_challenge.data.feature.countries.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.countries_challenge.data.feature.countries.local.entity.CityEntity

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCities(countries: List<CityEntity>)

    @Query(
        """
          SELECT * FROM cities
          WHERE LOWER(name) IN (:cityNames) 
          AND (NOT :showOnlyFavorites OR isFavourite = 1)
          ORDER BY name 
          LIMIT :pageSize OFFSET :offset
          """
    )
    fun getCitiesByNamePaged(
        cityNames: List<String>,
        pageSize: Int,
        offset: Int,
        showOnlyFavorites: Boolean = false
    ): List<CityEntity>

    @Query(
        """
          SELECT * FROM cities 
          WHERE (NOT :showOnlyFavorites OR isFavourite = 1)
          ORDER BY name ASC 
          LIMIT :pageSize OFFSET :offset
          """
    )
    fun getCitiesPaged(
        pageSize: Int,
        offset: Int,
        showOnlyFavorites: Boolean = false
    ): List<CityEntity>

    @Query("SELECT * FROM cities WHERE isFavourite = 1 ORDER BY name")
    fun getFavoriteCities(): List<CityEntity>

    @Update
    fun updateCity(city: CityEntity)

    @Query("SELECT COUNT(*) FROM cities")
    fun getCitiesCount(): Int

    @Query("SELECT name FROM cities")
    fun getAllCityNames(): List<String>
}