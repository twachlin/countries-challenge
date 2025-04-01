package com.example.countries_challenge.presentation.feature.mainnavigation.screens.portrait

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.countries_challenge.presentation.feature.mainnavigation.components.CityListItem
import com.example.countries_challenge.presentation.feature.mainnavigation.components.CityListItemUiModel
import com.example.countries_challenge.presentation.feature.mainnavigation.components.SmallLoader
import com.example.countries_challenge.presentation.feature.maps.components.CitiesMap

@Composable
fun PortraitScreen(
    cities: List<CityListItemUiModel>,
    showMap: Boolean,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    isLoadingMoreCities: Boolean = false,
) {
    Box(modifier = modifier) {
        if (showMap) {
            CitiesMap(modifier = Modifier.fillMaxSize())
        } else {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
            ) {
                items(cities.size) { index ->
                    CityListItem(
                        modifier = Modifier
                            .background(if (index % 2 == 0) Color.LightGray else Color.Transparent)
                            .padding(horizontal = 8.dp),
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
    }
}

@Preview(showBackground = true)
@Composable
private fun PortraitScreenPreview() {
    PortraitScreen(
        cities = listOf(
            CityListItemUiModel("AR", "La Plata", -34.92, -57.95, false),
            CityListItemUiModel("AR", "Buenos Aires", -34.92, -57.95, false),
            CityListItemUiModel("AR", "Ensenada", -34.92, -57.95, false),
            CityListItemUiModel("AR", "Berisso", -34.92, -57.95, false),
        ),
        showMap = false,
        lazyListState = rememberLazyListState()
    )
}