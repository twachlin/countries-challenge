package com.example.countries_challenge.presentation.feature.mainnavigation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.rememberNavController
import com.example.countries_challenge.presentation.feature.mainnavigation.components.BigLoader
import com.example.countries_challenge.presentation.feature.mainnavigation.screens.landscape.LandscapeScreen
import com.example.countries_challenge.presentation.feature.mainnavigation.screens.portrait.PortraitNavigation
import com.example.countries_challenge.presentation.viewmodel.cities.CitiesViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(FlowPreview::class)
@Composable
fun CitiesListScreen(viewModel: CitiesViewModel) {
    var isFirstTime by rememberSaveable { mutableStateOf(false) }
    val state = viewModel.screenState.collectAsState()
    val listState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val shouldLoadMore = remember {
        derivedStateOf {
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= (totalItemsCount - 5) && !state.value.isLoadingMoreCities
        }
    }
    val debouncedSearchValue = remember { derivedStateOf { state.value.searchValue } }
    val markerState = rememberMarkerState()
    val cameraPositionState = rememberCameraPositionState()
    val portraitScreenNavController = rememberNavController()

    if (state.value.isLoading) {
        BigLoader(modifier = Modifier.fillMaxSize())
    } else {

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LandscapeScreen(
                modifier = Modifier.fillMaxSize(),
                cities = state.value.cities,
                lazyListState = listState,
                isLoadingMoreCities = state.value.isLoadingMoreCities,
                markerState = markerState,
                cameraPositionState = cameraPositionState,
                searchValue = state.value.searchValue,
                onSearchValueChange = viewModel::updateSearchValue,
                onFilterByFavouritesClick = viewModel::onFavoriteFilterButtonClick,
                onCityClick = { index -> viewModel.onCityClick(index) },
                onFavoriteIconClick = viewModel::onFavoriteIconClick,
                isFavoritesFilterActive = state.value.isFavoriteFilterActive,
            )
        } else {
            PortraitNavigation(
                modifier = Modifier.fillMaxSize(),
                cities = state.value.cities,
                lazyListState = listState,
                isLoadingMoreCities = state.value.isLoadingMoreCities,
                markerState = markerState,
                cameraPositionState = cameraPositionState,
                onCityClick = { index -> viewModel.onCityClick(index) },
                navController = portraitScreenNavController,
                searchValue = state.value.searchValue,
                onSearchValueChange = viewModel::updateSearchValue,
                onFilterByFavouritesClick = viewModel::onFavoriteFilterButtonClick,
                onFavoriteIconClick = viewModel::onFavoriteIconClick,
                isFavoritesFilterActive = state.value.isFavoriteFilterActive,
            )
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it }  // Ensure that we load more items only when needed
            .collect {
                val prefix = state.value.searchValue.takeIf { it.isNotBlank() }
                viewModel.loadMoreCities(prefix = prefix)
            }
    }

    LaunchedEffect(debouncedSearchValue) {
        snapshotFlow { debouncedSearchValue.value }
            .debounce(300)
            .collect {
                if (isFirstTime) {
                    isFirstTime = false
                } else {
                    viewModel.makeNewSearch()
                }
            }

    }

    DisposableEffect(Unit) {
        onDispose {
            isFirstTime = true
        }
    }

    LaunchedEffect(state.value.selectedCity) {
        state.value.selectedCity?.let {
            val position = LatLng(it.lat, it.lon)
            markerState.position = position
            cameraPositionState.position = CameraPosition.fromLatLngZoom(position, 10f)
        }
    }
}