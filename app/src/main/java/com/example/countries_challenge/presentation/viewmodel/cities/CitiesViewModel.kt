package com.example.countries_challenge.presentation.viewmodel.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countries_challenge.domain.extensions.onSuccess
import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.domain.feature.countries.usecase.CitiesPagedModel
import com.example.countries_challenge.domain.feature.countries.usecase.GetCitiesPagedUseCase
import com.example.countries_challenge.domain.feature.countries.usecase.ImportCountriesUseCase
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

    private var citiesPage = 1
    private val citiesModel = mutableListOf<CityModel>()
    private var isLastPage = false

    /**
     * Imports cities if the local database is empty. And then fetches the first page of cities.
     */
    fun getCities() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                importCountriesUseCase.execute(Unit).onSuccess {
                    getCitiesPagedUseCase.execute(
                        params = GetCitiesPagedUseCase.Params(
                            page = citiesPage,
                            prefix = null,
                        )
                    ).onSuccess { model ->
                        updateCities(citiesPagedModel = model)
                        _screenState.update { state -> state.copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private fun getCitiesPaged(prefix: String?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getCitiesPagedUseCase.execute(
                    params = GetCitiesPagedUseCase.Params(
                        page = citiesPage,
                        prefix = prefix,
                    )
                ).onSuccess { model ->
                    updateCities(citiesPagedModel = model)
                }
                citiesPage++
            }
        }
    }

    private fun updateCities(citiesPagedModel: CitiesPagedModel) {
        isLastPage = citiesPagedModel.isLastPage
        citiesModel.addAll(citiesPagedModel.cities)
        val citiesUiModel = citiesPagedModel.cities.map { it.toCityListItemUIModel() }
        _screenState.update { state ->
            state.copy(
                cities = state.cities + citiesUiModel,
                isLoadingMoreCities = false,
            )
        }
    }

    fun loadMoreCities(prefix: String?) {
        if (!isLastPage) {
            _screenState.update { state -> state.copy(isLoadingMoreCities = true) }
            getCitiesPaged(prefix = prefix)
        }
    }

    fun updateSearchValue(value: String) {
        _screenState.update { state -> state.copy(searchValue = value) }
    }

    fun onSearchValueChange(value: String) {
        resetData()
        getCitiesPaged(value)
    }

    private fun resetData() {
        citiesPage = 1
        _screenState.update { state -> state.copy(cities = emptyList()) }
    }
}

data class CitiesListScreenState(
    val cities: List<CityListItemUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val showMap: Boolean = false,
    val searchValue: String = "",
    val isLoadingMoreCities: Boolean = false,
)