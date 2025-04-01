package com.example.countries_challenge.presentation.feature.mainnavigation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.countries_challenge.presentation.feature.mainnavigation.components.BigLoader
import com.example.countries_challenge.presentation.feature.mainnavigation.components.TopBarContent
import com.example.countries_challenge.presentation.feature.mainnavigation.screens.landscape.LandscapeScreen
import com.example.countries_challenge.presentation.feature.mainnavigation.screens.portrait.PortraitScreen
import com.example.countries_challenge.presentation.viewmodel.cities.CitiesViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(FlowPreview::class)
@Composable
fun CitiesListScreen(
    viewModel: CitiesViewModel,
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
    val debouncedSearchValue = remember { derivedStateOf { state.value.searchValue } }

    if (state.value.isLoading) {
        BigLoader(modifier = Modifier.fillMaxSize())
    } else {
        Scaffold(
            topBar = {
                TopBarContent(
                    modifier = Modifier.fillMaxWidth(),
                    searchValue = state.value.searchValue,
                    onSearchValueChange = viewModel::updateSearchValue,
                    onFilterByFavouritesClick = onFilterByFavouritesClick,
                )
            },
            content = { paddingValues ->
                if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    LandscapeScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        cities = state.value.cities,
                        lazyListState = listState,
                        isLoadingMoreCities = state.value.isLoadingMoreCities,
                    )
                } else {
                    PortraitScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        cities = state.value.cities,
                        showMap = state.value.showMap,
                        lazyListState = listState,
                        isLoadingMoreCities = state.value.isLoadingMoreCities,
                    )
                }
            }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.getCities()
    }

    LaunchedEffect(listState) {
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
            .distinctUntilChanged()
            .collect {
                viewModel.onSearchValueChange(it)
            }
    }
}