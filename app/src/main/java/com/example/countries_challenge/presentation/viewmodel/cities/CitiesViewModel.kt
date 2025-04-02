package com.example.countries_challenge.presentation.viewmodel.cities

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countries_challenge.domain.extensions.onSuccess
import com.example.countries_challenge.domain.feature.countries.model.CitiesPagedModel
import com.example.countries_challenge.domain.feature.countries.model.CityModel
import com.example.countries_challenge.domain.feature.countries.usecase.GetCitiesPagedUseCase
import com.example.countries_challenge.domain.feature.countries.usecase.ImportCountriesUseCase
import com.example.countries_challenge.domain.feature.countries.usecase.RemoveCityFromFavoritesUseCase
import com.example.countries_challenge.domain.feature.countries.usecase.SetCityAsFavoriteUseCase
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
    private val setCityAsFavoriteUseCase: SetCityAsFavoriteUseCase,
    private val removeCityFromFavoritesUseCase: RemoveCityFromFavoritesUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow(CitiesListScreenState())
    val screenState: StateFlow<CitiesListScreenState> = _screenState

    private var citiesPage = 1
    private val citiesModel = mutableListOf<CityModel>()
    private var isLastPage = false

    init {
        importCitiesIfNecessary()
    }

    /**
     * Imports cities if the local database is empty. And then fetches the first page of cities.
     */
    fun importCitiesIfNecessary() {
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
                        showOnlyFavorites = screenState.value.isFavoriteFilterActive
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
                isLoading = false,
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

    fun makeNewSearch() {
        resetData()
        getCitiesPaged(screenState.value.searchValue.takeIf { it.isNotBlank() })
    }

    private fun resetData() {
        citiesPage = 1
        isLastPage = false
        _screenState.update { state -> state.copy(cities = emptyList()) }
    }

    fun onCityClick(index: Int) {
        _screenState.update { state ->
            state.copy(selectedCity = state.cities[index])
        }
    }

    private fun setCityAsUpdatingFavoriteState(id: Int) {
        _screenState.update { state ->
            state.copy(
                cities = state.cities.mapIndexed { index, city ->
                    if (city.id == id) {
                        city.copy(isUpdatingFavoriteState = true)
                    } else {
                        city
                    }
                }
            )
        }
    }

    fun onFavoriteFilterButtonClick() {
        _screenState.update { state ->
            state.copy(
                isLoading = true,
                isFavoriteFilterActive = !state.isFavoriteFilterActive
            )
        }
        makeNewSearch()
    }

    fun onFavoriteIconClick(id: Int) {
        val cityModel = citiesModel.firstOrNull { it.id == id }
        cityModel?.let {
            setCityAsUpdatingFavoriteState(id = id)
            if (cityModel.isFavourite) {
                removeCityFromFavorites(cityModel = cityModel)
            } else {
                addCityToFavorites(cityModel = cityModel)
            }
        }
    }

    fun addCityToFavorites(cityModel: CityModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setCityAsFavoriteUseCase.execute(
                    params = SetCityAsFavoriteUseCase.Params(city = cityModel)
                ).onSuccess {
                    // 1. Update the local list (citiesModel)
                    val localCityIndex = citiesModel.indexOfFirst { it.id == cityModel.id }
                    if (localCityIndex != -1) {
                        citiesModel[localCityIndex] = cityModel.copy(isFavourite = true)

                        _screenState.update { state ->
                            state.copy(
                                cities = state.cities.map { uiModel ->
                                    if (uiModel.id == cityModel.id) {
                                        uiModel.copy(
                                            isFavourite = true,
                                            isUpdatingFavoriteState = false
                                        )
                                    } else {
                                        uiModel
                                    }
                                }
                            )
                        }
                    }

                }
            }
        }
    }

    fun removeCityFromFavorites(cityModel: CityModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                removeCityFromFavoritesUseCase.execute(
                    params = RemoveCityFromFavoritesUseCase.Params(city = cityModel)
                ).onSuccess {
                    // 1. Update the local list (citiesModel)
                    val localCityIndex = citiesModel.indexOfFirst { it.id == cityModel.id }
                    if (localCityIndex != -1) {
                        citiesModel[localCityIndex] = cityModel.copy(isFavourite = false)

                        _screenState.update { state ->
                            state.copy(
                                cities = state.cities.map { uiModel ->
                                    if (uiModel.id == cityModel.id) {
                                        uiModel.copy(
                                            isFavourite = false,
                                            isUpdatingFavoriteState = false
                                        )
                                    } else {
                                        uiModel
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Stable
data class CitiesListScreenState(
    val cities: List<CityListItemUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val searchValue: String = "",
    val isLoadingMoreCities: Boolean = false,
    val selectedCity: CityListItemUiModel? = null,
    val isFavoriteFilterActive: Boolean = false,
)