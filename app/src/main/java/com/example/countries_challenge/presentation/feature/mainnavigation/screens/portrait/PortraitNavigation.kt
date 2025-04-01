package com.example.countries_challenge.presentation.feature.mainnavigation.screens.portrait

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.countries_challenge.R
import com.example.countries_challenge.presentation.feature.mainnavigation.components.CityListItem
import com.example.countries_challenge.presentation.feature.mainnavigation.components.CityListItemUiModel
import com.example.countries_challenge.presentation.feature.mainnavigation.components.SmallLoader
import com.example.countries_challenge.presentation.feature.mainnavigation.components.TopBarContent
import com.example.countries_challenge.presentation.feature.maps.components.CitiesMap
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.serialization.Serializable

@Serializable
object PortraitCitiesList

@Serializable
object PortraitMap

@Composable
fun PortraitNavigation(
    navController: NavHostController,
    cities: List<CityListItemUiModel>,
    lazyListState: LazyListState,
    onCityClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    isLoadingMoreCities: Boolean = false,
    markerState: MarkerState,
    cameraPositionState: CameraPositionState,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onFilterByFavouritesClick: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PortraitCitiesList,
    ) {
        composable<PortraitCitiesList> {
            PortraitScreenCitiesContent(
                modifier = Modifier.fillMaxSize(),
                cities = cities,
                listState = lazyListState,
                onCityClick = { index ->
                    onCityClick(index)
                    navController.navigate(PortraitMap)
                },
                isLoadingMoreCities = isLoadingMoreCities,
                searchValue = searchValue,
                onSearchValueChange = onSearchValueChange,
                onFilterByFavouritesClick = onFilterByFavouritesClick,
            )
        }

        composable<PortraitMap> {
            PortraitScreenMapContent(
                modifier = Modifier.fillMaxSize(),
                markerState = markerState,
                cameraPositionState = cameraPositionState,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun PortraitScreenCitiesContent(
    cities: List<CityListItemUiModel>,
    listState: LazyListState,
    onCityClick: (index: Int) -> Unit,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onFilterByFavouritesClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoadingMoreCities: Boolean = false,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBarContent(
                modifier = Modifier.fillMaxWidth(),
                searchValue = searchValue,
                onSearchValueChange = onSearchValueChange,
                onFilterByFavouritesClick = onFilterByFavouritesClick,
            )
        },
        content = { paddingValues ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                items(cities.size) { index ->
                    CityListItem(
                        modifier = Modifier
                            .background(if (index % 2 == 0) Color.LightGray else Color.Transparent)
                            .padding(horizontal = 8.dp)
                            .clickable { onCityClick(index) },
                        model = cities[index],
                        onFavouriteIconClick = {},
                    )
                }
                if (isLoadingMoreCities) {
                    item {
                        SmallLoader(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun PortraitScreenMapContent(
    markerState: MarkerState,
    cameraPositionState: CameraPositionState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(onClick = onBackClick)
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = stringResource(R.string.back)
                )
            }
        },
        content = { paddingValues ->
            CitiesMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                marketState = markerState,
                cameraPositionState = cameraPositionState,
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PortraitScreenPreview() {
    PortraitNavigation(
        cities = listOf(
            CityListItemUiModel("AR", "La Plata", -34.92, -57.95, false),
            CityListItemUiModel("AR", "Buenos Aires", -34.92, -57.95, false),
            CityListItemUiModel("AR", "Ensenada", -34.92, -57.95, false),
            CityListItemUiModel("AR", "Berisso", -34.92, -57.95, false),
        ),
        lazyListState = rememberLazyListState(),
        markerState = rememberMarkerState(),
        cameraPositionState = rememberCameraPositionState(),
        onCityClick = {},
        navController = rememberNavController(),
        searchValue = "",
        onSearchValueChange = {},
        onFilterByFavouritesClick = {},
    )
}