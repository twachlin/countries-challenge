package com.example.countries_challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.countries_challenge.presentation.feature.mainnavigation.MainNavigation
import com.example.countries_challenge.presentation.viewmodel.cities.CitiesViewModel
import com.example.countries_challenge.ui.theme.CountriesChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CitiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CountriesChallengeTheme {
                MainNavigation(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}