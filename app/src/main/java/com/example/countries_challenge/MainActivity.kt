package com.example.countries_challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.countries_challenge.presentation.feature.maps.components.CitiesMap
import com.example.countries_challenge.ui.theme.CountriesChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountriesChallengeTheme {
                CitiesMap(modifier = Modifier.fillMaxSize())
            }
        }
    }
}