package com.example.countries_challenge.presentation.feature.mainnavigation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.countries_challenge.R
import com.example.countries_challenge.presentation.components.buttons.FilterButton

@Composable
fun TopBarContent(
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onFilterByFavouritesClick: () -> Unit,
    isFavoritesFilterActive: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SearchField(
            modifier = Modifier
                .fillMaxWidth(),
            value = searchValue,
            onValueChange = { newValue -> onSearchValueChange(newValue) },
        )
        FilterButton(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(vertical = 8.dp),
            text = stringResource(R.string.show_favourites),
            onClick = { onFilterByFavouritesClick() },
            isActive = isFavoritesFilterActive,
        )
    }
}