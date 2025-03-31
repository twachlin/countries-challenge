package com.example.countries_challenge.presentation.viewmodel.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countries_challenge.domain.extensions.onSuccess
import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.domain.feature.countries.usecase.GetCitiesPagedUseCase
import com.example.countries_challenge.domain.feature.countries.usecase.ImportCountriesUseCase
import com.example.countries_challenge.domain.feature.search.usecase.GetCountriesByPrefixUseCase
import com.example.countries_challenge.presentation.feature.mainnavigation.components.CityListItemUiModel
import com.example.countries_challenge.presentation.feature.mainnavigation.screens.model.toCityListItemUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val importCountriesUseCase: ImportCountriesUseCase,
    private val getCitiesPagedUseCase: GetCitiesPagedUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow(CitiesListScreenState())
    val screenState: StateFlow<CitiesListScreenState> = _screenState
    var citiesPage = 1
    private val citiesModel = mutableListOf<CityModel>()

    /**
     * Imports cities if the local database is empty. And then fetches the first page of cities.
     */
    fun getCities() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                importCountriesUseCase.execute(Unit).onSuccess {
                    getCitiesPagedUseCase.execute(
                        params = GetCitiesPagedUseCase.Params(
                            page = citiesPage
                        )
                    ).onSuccess { cities ->
                        updateCities(cities)
                        _screenState.update { state -> state.copy(isLoading = false) }
                    }
                }
            }
        }
    }

    fun getCitiesPaged() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _screenState.update { state -> state.copy(isLoadingMoreCities = true) }
                getCitiesPagedUseCase.execute(
                    params = GetCitiesPagedUseCase.Params(
                        page = citiesPage
                    )
                ).onSuccess { cities ->
                    updateCities(cities = cities)
                }
                citiesPage++
            }
        }
    }

    private fun updateCities(cities: List<CityModel>) {
        citiesModel.addAll(cities)
        val citiesUiModel = cities.map { it.toCityListItemUIModel() }
        _screenState.update { state ->
            state.copy(
                cities = state.cities + citiesUiModel,
                isLoadingMoreCities = false,
            )
        }
    }
}

data class CitiesListScreenState(
    val cities: List<CityListItemUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val showMap: Boolean = false,
    val searchValue: String = "",
    val isLoadingMoreCities: Boolean = false,
)