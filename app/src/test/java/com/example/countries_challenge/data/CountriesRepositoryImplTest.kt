package com.example.countries_challenge.data

import android.util.Log
import com.example.countries_challenge.data.feature.countries.local.CountriesLocalDataSource
import com.example.countries_challenge.data.feature.countries.local.entity.CityEntity
import com.example.countries_challenge.data.feature.countries.net.CountriesDataSource
import com.example.countries_challenge.data.feature.countries.repository.CountriesRepositoryImpl
import com.example.countries_challenge.domain.feature.countries.model.CityLocationModel
import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.domain.utils.UseCaseResult
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CountriesRepositoryImplTest {

    private val countriesDataSource: CountriesDataSource = mockk()
    private val countriesLocalDataSource: CountriesLocalDataSource = mockk()

    private val repository = CountriesRepositoryImpl(countriesDataSource, countriesLocalDataSource)

    @Test
    fun `GIVEN cities WHEN getCitiesPaged is called without prefix THEN return cities`() =
        runTest {
            // Given
            val page = 1
            val pageSize = 2
            val mockEntities = listOf<CityEntity>(
                CityEntity(
                    id = 1,
                    name = "City 1",
                    isFavourite = false,
                    lat = 0.0,
                    lon = 0.0,
                    country = "CO"
                )
            )
            val cityModels = listOf(
                CityModel(
                    id = 1,
                    name = "City 1",
                    isFavourite = false,
                    country = "CO",
                    coordinates = CityLocationModel(lat = 0.0, lon = 0.0)
                )
            )

            coEvery {
                countriesLocalDataSource.getCitiesPaged(
                    any(),
                    any(),
                    any()
                )
            } returns mockEntities

            // When
            val result = repository.getCitiesPaged(page, pageSize, null, false)

            // Then
            assertTrue(result is UseCaseResult.Success)
            assertEquals(cityModels, (result as UseCaseResult.Success).data.cities)
            assertTrue(result.data.isLastPage)
        }

    @Test
    fun `GIVEN fewer cities than pageSize WHEN getCitiesPaged is called THEN return isLastPage true`() =
        runTest {
            // Given
            val page = 1
            val pageSize = 3
            val mockEntities = listOf<CityEntity>(
                CityEntity(
                    id = 1,
                    name = "City 1",
                    isFavourite = false,
                    lat = 0.0,
                    lon = 0.0,
                    country = "CO"
                )
            )
            val cityModels = listOf(
                CityModel(
                    id = 1,
                    name = "City 1",
                    isFavourite = false,
                    country = "CO",
                    coordinates = CityLocationModel(lat = 0.0, lon = 0.0)
                )
            )

            coEvery {
                countriesLocalDataSource.getCitiesPaged(
                    pageSize,
                    0,
                    false
                )
            } returns mockEntities

            // When
            val result = repository.getCitiesPaged(page, pageSize, null, false)

            // Then
            assertTrue(result is UseCaseResult.Success)
            assertEquals(cityModels, (result as UseCaseResult.Success).data.cities)
            assertTrue(result.data.isLastPage)
        }

    @Test
    fun `GIVEN an error occurs WHEN getCitiesPaged is called THEN return error`() = runTest {
        // Given
        val page = 1
        val pageSize = 2

        coEvery { countriesLocalDataSource.getCitiesPaged(pageSize, 0, false) } throws Exception()
        coEvery { Log.e(any(), any()) } returns 0

        // When
        val result = repository.getCitiesPaged(page, pageSize, null, false)

        // Then
        assertTrue(result is UseCaseResult.Error)
    }
}
