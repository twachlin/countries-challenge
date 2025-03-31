package com.example.countries_challenge.presentation.feature.mainnavigation.screens.citydetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.countries_challenge.R
import com.example.countries_challenge.presentation.viewmodel.cities.CitiesViewModel

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

    LaunchedEffect(Unit) {
        // TODO: Fetch city details
    }
}