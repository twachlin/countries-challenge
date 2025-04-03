package com.example.countries_challenge.presentation.feature.mainnavigation.screens.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.countries_challenge.R
import com.example.countries_challenge.presentation.components.buttons.PrimaryButton

@Composable
fun ErrorScreen(
    onRetryButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(R.string.error_screen_title)
        )
        PrimaryButton(
            text = stringResource(R.string.retry),
            onClick = onRetryButtonClick
        )
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    ErrorScreen(onRetryButtonClick = {})
}