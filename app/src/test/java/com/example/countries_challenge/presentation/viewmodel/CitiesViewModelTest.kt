package com.example.countries_challenge.presentation.viewmodel

import com.example.countries_challenge.domain.feature.countries.model.CityLocationModel
import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.domain.feature.countries.usecase.GetCitiesPagedUseCase
import com.example.countries_challenge.domain.feature.countries.usecase.ImportCountriesUseCase
import com.example.countries_challenge.domain.feature.countries.usecase.RemoveCityFromFavoritesUseCase
import com.example.countries_challenge.domain.feature.countries.usecase.SetCityAsFavoriteUseCase
import com.example.countries_challenge.domain.utils.UseCaseResult
import com.example.countries_challenge.presentation.viewmodel.cities.CitiesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CitiesViewModelTest {
    private val importCountriesUseCase: ImportCountriesUseCase = mockk()
    private val getCitiesPagedUseCase: GetCitiesPagedUseCase = mockk()
    private val setCityAsFavoriteUseCase: SetCityAsFavoriteUseCase = mockk()
    private val removeCityFromFavoritesUseCase: RemoveCityFromFavoritesUseCase = mockk()

    private lateinit var viewModel: CitiesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = CitiesViewModel(
            importCountriesUseCase = importCountriesUseCase,
            getCitiesPagedUseCase = getCitiesPagedUseCase,
            setCityAsFavoriteUseCase = setCityAsFavoriteUseCase,
            removeCityFromFavoritesUseCase = removeCityFromFavoritesUseCase
        )
        coEvery { importCountriesUseCase.execute(Unit) } returns UseCaseResult.Success(Unit)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN a new search value WHEN updateSearchValue is called THEN screenState is updated`() =
        runTest {
            // Given
            val newSearchValue = "New York"
            // When
            viewModel.updateSearchValue(newSearchValue)

            // Then
            assertEquals(newSearchValue, viewModel.screenState.value.searchValue)
        }

    @Test
    fun `GIVEN a city WHEN removeCityFromFavorites is called THEN removeCityFromFavoritesUseCase is called`() {
        runBlocking {
            // Given
            val city = CityModel("US", "Denver", 1, true, CityLocationModel(1.0, 1.0))
            coEvery { removeCityFromFavoritesUseCase.execute(any()) } returns UseCaseResult.Success(
                Unit
            )

            // When
            viewModel.removeCityFromFavorites(city)

            // Then
            coVerify { removeCityFromFavoritesUseCase.execute(any()) }
        }
    }

    @Test
    fun `GIVEN a city WHEN addCityToFavorites is called THEN setCityAsFavoriteUseCase is called`() {
        runBlocking {
            // Given
            val city = CityModel("US", "Denver", 1, false, CityLocationModel(1.0, 1.0))
            coEvery { setCityAsFavoriteUseCase.execute(any()) } returns UseCaseResult.Success(Unit)

            // When
            viewModel.addCityToFavorites(city)

            // Then
            coVerify { setCityAsFavoriteUseCase.execute(any()) }
        }
    }
}