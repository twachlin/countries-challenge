package com.example.countries_challenge.presentation.feature.mainnavigation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.countries_challenge.R
import com.example.countries_challenge.presentation.components.buttons.SecondaryButton

data class CityListItemUiModel(
    val id: Int,
    val country: String = "",
    val name: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val isFavourite: Boolean = false,
    val isUpdatingFavoriteState: Boolean = false,
)

@Composable
fun CityListItem(
    model: CityListItemUiModel,
    onFavouriteIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(text = stringResource(R.string.city_title, model.name, model.country))
            Spacer(modifier = Modifier.size(2.dp))
            Text(text = stringResource(R.string.city_subtitle, model.lat, model.lon))
        }
        SecondaryButton(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(id = R.string.details),
            onClick = {},
        )
        Box(
            modifier = Modifier
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (model.isUpdatingFavoriteState) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(onClick = onFavouriteIconClick),
                    painter = painterResource(
                        id = if (model.isFavourite) {
                            R.drawable.ic_favorite_filled
                        } else {
                            R.drawable.ic_favourite_border
                        }
                    ),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CityListItemPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CityListItem(
            modifier = Modifier.fillMaxWidth(),
            onFavouriteIconClick = {},
            model = CityListItemUiModel(
                country = "AR",
                name = "La Plata",
                lat = -34.92,
                lon = -57.95,
                isFavourite = false,
                isUpdatingFavoriteState = false,
                id = 1,
            ),
        )
        CityListItem(
            modifier = Modifier.fillMaxWidth(),
            onFavouriteIconClick = {},
            model = CityListItemUiModel(
                country = "AR",
                name = "La Plata",
                lat = -34.92567,
                lon = -57.95656,
                isFavourite = true,
                isUpdatingFavoriteState = false,
                id = 1,
            ),
        )
        CityListItem(
            modifier = Modifier.fillMaxWidth(),
            onFavouriteIconClick = {},
            model = CityListItemUiModel(
                country = "AR",
                name = "La Plata",
                lat = -34.92567,
                lon = -57.95656,
                isFavourite = true,
                isUpdatingFavoriteState = true,
                id = 1,
            ),
        )
    }
}