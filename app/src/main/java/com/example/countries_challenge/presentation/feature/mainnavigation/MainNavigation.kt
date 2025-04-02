package com.example.countries_challenge.presentation.feature.mainnavigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.countries_challenge.presentation.feature.mainnavigation.screens.CitiesListScreen
import com.example.countries_challenge.presentation.feature.mainnavigation.screens.citydetail.CityDetailScreen
import com.example.countries_challenge.presentation.viewmodel.cities.CitiesViewModel
import kotlinx.serialization.Serializable

@Serializable
object CitiesList

@Serializable
class CityDetails(
    val cityId: Int,
)

@Composable
fun MainNavigation(
    viewModel: CitiesViewModel,
    navController: NavHostController
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = CitiesList
    ) {
        composable<CitiesList> {
            CitiesListScreen(viewModel = viewModel)
        }
        composable<CityDetails> { backStackEntry ->
            val cityDetails: CityDetails = backStackEntry.toRoute()
            CityDetailScreen(
                viewModel = viewModel,
                cityId = cityDetails.cityId
            )
        }
    }
}