package com.example.countries_challenge.presentation.feature.maps.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap

@Composable
fun CitiesMap(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
        )
    }

}