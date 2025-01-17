package com.geekofia.dugulink.root.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.geekofia.dugulink.MainActivity
import com.geekofia.dugulink.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RootScreen(onLoginSuccess: () -> Unit,onNavigateToOnboarding: () -> Unit,  mainViewModel: MainViewModel) {
    val user = FirebaseAuth.getInstance().currentUser
    val onboardingCompleted = mainViewModel.isOnboardingCompleted.collectAsState().value

    // No UI elements
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Loading...", fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    }

    // Navigate based on the auth state and onboarding status
    LaunchedEffect(user, onboardingCompleted) {
        // Check onboarding completion
        if (onboardingCompleted) {
            // If the user is logged in, and onboarding completed, go to dashboard
            if (user != null) {
                onLoginSuccess()

            } else {
                // If the user is not logged in, navigate to the login screen
                mainViewModel.handleNavigationToSignIn()
            }
        } else {
            // If onboarding is not completed, navigate to the onboarding screen
            onNavigateToOnboarding()
        }
    }
}

@Composable
fun LinearDeterminateIndicator() {
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Create a coroutine scope

    LaunchedEffect(Unit) {
        loading = true
        scope.launch {
            loadProgress { progress ->
                currentProgress = progress
            }
            loading = false // Reset loading when the coroutine finishes
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (loading) {
            LinearProgressIndicator(
                progress = currentProgress,
                modifier = Modifier
                    .padding(36.dp)
                    .fillMaxWidth()
            )
        }
    }
}

suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}
