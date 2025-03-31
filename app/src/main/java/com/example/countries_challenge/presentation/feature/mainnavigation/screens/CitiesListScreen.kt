package com.example.countries_challenge.presentation.feature.mainnavigation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.countries_challenge.presentation.feature.mainnavigation.components.BigLoader
import com.example.countries_challenge.presentation.feature.mainnavigation.screens.landscape.LandscapeScreen
import com.example.countries_challenge.presentation.feature.mainnavigation.screens.portrait.PortraitScreen
import com.example.countries_challenge.presentation.viewmodel.cities.CitiesViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun CitiesListScreen(
    viewModel: CitiesViewModel,
    onSearchValueChange: (String) -> Unit,
    onFilterByFavouritesClick: () -> Unit,
) {
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
    if (state.value.isLoading) {
        BigLoader(modifier = Modifier.fillMaxSize())
    } else {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LandscapeScreen(
                cities = state.value.cities,
                searchValue = state.value.searchValue,
                onSearchValueChange = onSearchValueChange,
                onFilterByFavouritesClick = onFilterByFavouritesClick,
                lazyListState = listState,
                isLoadingMoreCities = state.value.isLoadingMoreCities,
            )
        } else {
            PortraitScreen(
                cities = state.value.cities,
                searchValue = state.value.searchValue,
                onSearchValueChange = onSearchValueChange,
                onFilterByFavouritesClick = onFilterByFavouritesClick,
                showMap = state.value.showMap,
                lazyListState = listState,
                isLoadingMoreCities = state.value.isLoadingMoreCities,
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getCities()
    }

    LaunchedEffect(listState) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it }  // Ensure that we load more items only when needed
            .collect {
                viewModel.getCitiesPaged()
            }
    }
}