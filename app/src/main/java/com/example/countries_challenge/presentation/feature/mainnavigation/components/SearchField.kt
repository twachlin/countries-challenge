package com.example.countries_challenge.presentation.feature.mainnavigation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.countries_challenge.R

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        placeholder = {
            Text(text = stringResource(R.string.filter))
        },
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(painter = painterResource(R.drawable.ic_search), contentDescription = null)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchFieldPreview() {
    SearchField(
        value = "",
        onValueChange = {},
    )
}