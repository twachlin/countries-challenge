package com.example.countries_challenge.presentation.feature.mainnavigation.screens.citydetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.countries_challenge.R
import com.example.countries_challenge.presentation.viewmodel.cities.CitiesViewModel

/**
 * Screen only for navigation purposes.
 * If we have an api with city details we can ask for it and show it here.
 */
@Composable
fun CityDetailScreen(
    viewModel: CitiesViewModel,
    cityId: Int,
) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.detail_screen_title, cityId))
    }
}