package com.geekofia.dugulink.onboarding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingFooter(
    currentPage: Int,
    totalPages: Int,
    onNextClicked: () -> Unit,
    onSkipClicked: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(totalPages) { index ->
                val color =
                    if (index == currentPage) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.3f
                        )
                    }
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(8.dp)
                        .background(color, shape = CircleShape)
                )
            }
        }

        Button(onClick = onNextClicked) {
            Text(if (currentPage == totalPages - 1) "Finish" else "Next")
        }
    }
}
