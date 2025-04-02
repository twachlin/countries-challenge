package com.example.countries_challenge.presentation.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.countries_challenge.R

@Composable
fun FilterButton(
    text: String,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable () -> Unit = {},
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(50f)
            )
            .clip(shape = RoundedCornerShape(50f))
            .background(
                color = if (isActive) {
                    MaterialTheme.colorScheme.inversePrimary
                } else {
                    Color.Transparent
                }
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        leadingIcon()
        Text(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            text = text,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
private fun FilterButtonPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        FilterButton(
            text = "Filter",
            isActive = false
        )
        FilterButton(
            text = "Filter",
            isActive = true
        )
        FilterButton(
            text = "Filter",
            isActive = true,
            leadingIcon = {
                Box(modifier = Modifier.padding(start = 4.dp)) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(R.drawable.ic_filter),
                        contentDescription = null
                    )
                }
            }
        )
    }
}