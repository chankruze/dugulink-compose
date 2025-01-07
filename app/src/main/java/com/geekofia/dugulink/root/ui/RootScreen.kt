package com.geekofia.dugulink.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.geekofia.dugulink.MainViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RootScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val user = FirebaseAuth.getInstance().currentUser
    val onboardingCompleted = mainViewModel.isOnboardingCompleted.collectAsState().value

    // No UI elements

    // Navigate based on the auth state and onboarding status
    LaunchedEffect(user, onboardingCompleted) {
        // Check onboarding completion
        if (onboardingCompleted) {
            // If the user is logged in, and onboarding completed, go to dashboard
            if (user != null) {
                mainViewModel.handleLoginSuccess()
            } else {
                // If the user is not logged in, navigate to the login screen
                mainViewModel.handleNavigationToSignIn()
            }
        } else {
            // If onboarding is not completed, navigate to the onboarding screen
            navController.navigate("onboarding") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }
}
