package com.example.countries_challenge.domain

import com.example.countries_challenge.domain.feature.countries.model.CitiesPagedModel
import com.example.countries_challenge.domain.feature.countries.repository.CountriesRepository
import com.example.countries_challenge.domain.feature.countries.usecase.GetCitiesPagedUseCase
import com.example.countries_challenge.domain.utils.UseCaseResult
import com.example.countries_challenge.domain.utils.networkerror.NetworkError
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCitiesPagedUseCaseTest {

    private val repository: CountriesRepository = mockk()
    private val getCitiesPagedUseCase = GetCitiesPagedUseCase(repository)

    @Test
    fun `GIVEN successful response WHEN buildUseCase is called THEN return success`() = runTest {
        // Given
        val params =
            GetCitiesPagedUseCase.Params(page = 1, prefix = "New", showOnlyFavorites = true)
        val mockResponse: CitiesPagedModel = mockk(relaxed = true)
        coEvery {
            repository.getCitiesPaged(
                pageSize = 50,
                page = 1,
                prefix = "New",
                showOnlyFavorites = true
            )
        } returns UseCaseResult.Success(mockResponse)

        // When
        val result = getCitiesPagedUseCase.execute(params)

        // Then
        assertTrue(result is UseCaseResult.Success)
        assertEquals(mockResponse, (result as UseCaseResult.Success).data)
        coVerify {
            repository.getCitiesPaged(
                pageSize = 50,
                page = 1,
                prefix = "New",
                showOnlyFavorites = true
            )
        }
    }

    @Test
    fun `GIVEN error response WHEN buildUseCase is called THEN return error`() = runTest {
        // Given
        val params =
            GetCitiesPagedUseCase.Params(page = 1, prefix = null, showOnlyFavorites = false)
        coEvery {
            repository.getCitiesPaged(
                pageSize = 50,
                page = 1,
                prefix = null,
                showOnlyFavorites = false
            )
        } returns UseCaseResult.Error(NetworkError.UndefinedError())

        // When
        val result = getCitiesPagedUseCase.execute(params)

        // Then
        assertTrue(result is UseCaseResult.Error)
        coVerify {
            repository.getCitiesPaged(
                pageSize = 50,
                page = 1,
                prefix = null,
                showOnlyFavorites = false
            )
        }
    }
}
