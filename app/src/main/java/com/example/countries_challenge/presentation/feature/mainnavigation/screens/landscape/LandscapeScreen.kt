package com.example.countries_challenge.presentation.feature.mainnavigation.screens.landscape

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
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
fun LandscapeScreen(
    cities: List<CityListItemUiModel>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    isLoadingMoreCities: Boolean = false,
) {
    Row(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = lazyListState,
        ) {
            items(cities.size) { index ->
                CityListItem(
                    modifier = Modifier
                        .background(if (index % 2 == 0) Color.LightGray else Color.Transparent),
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
        CitiesMap(modifier = Modifier.weight(1f))
    }
}


@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
private fun LandScapeScreenPreview() {
    LandscapeScreen(
        cities = listOf(
            CityListItemUiModel("AR", "La Plata", -34.92, -57.95, false),
            CityListItemUiModel("AR", "Buenos Aires", -34.92, -57.95, false),
            CityListItemUiModel("AR", "Ensenada", -34.92, -57.95, false),
            CityListItemUiModel("AR", "Berisso", -34.92, -57.95, false),
        ),
        lazyListState = rememberLazyListState()
    )
}