package com.example.countries_challenge

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.countries_challenge.presentation.feature.mainnavigation.MainNavigation
import com.example.countries_challenge.presentation.viewmodel.cities.CitiesViewModel
import com.example.countries_challenge.presentation.viewmodel.cities.Event
import com.example.countries_challenge.ui.theme.CountriesChallengeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CitiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
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

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.event.collect { event ->
                when (event) {
                    is Event.ErrorAddingToFavorites -> {
                        showToastMessage(getString(R.string.error_adding_to_favorites))
                    }
                    is Event.ErrorRemovingFromFavorites -> {
                        showToastMessage(getString(R.string.error_removing_from_favorites))
                    }
                }
            }
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}