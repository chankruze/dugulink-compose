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
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        TextButton(onClick = onSkipClicked) {
            Text("Skip")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(totalPages) { index ->
                val color = if (index == currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                Box(
                    modifier = Modifier.size(8.dp).padding(2.dp).background(color, shape = CircleShape)
                )
            }
        }

        Button(onClick = onNextClicked) {
            Text(if (currentPage == totalPages - 1) "Finish" else "Next")
        }
    }
}
