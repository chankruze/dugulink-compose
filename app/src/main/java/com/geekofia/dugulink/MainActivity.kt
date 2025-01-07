package com.geekofia.dugulink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geekofia.dugulink.onboarding.ui.OnboardingScreen
import com.geekofia.dugulink.ui.theme.DuguLinkTheme
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.geekofia.dugulink.auth.ui.LoginScreen
import com.geekofia.dugulink.auth.ui.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val mainViewModel: MainViewModel = viewModel()
            val navigationDestination = mainViewModel.navigateTo.collectAsState().value

            DuguLinkTheme {
                // Observe navigation events
                LaunchedEffect(navigationDestination) {
                    navigationDestination?.let { destination ->
                        navController.navigate(destination) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "root",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("root") {
                            RootScreenContent(navController, mainViewModel)
                        }
                        composable("onboarding") {
                            OnboardingScreen(onFinish = {
                                mainViewModel.setOnboardingCompleted()
                            })
                        }
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = {
                                    mainViewModel.handleLoginSuccess()
                                },
                                onNavigateToSignUp = { mainViewModel.handleNavigationToSignUp() }
                            )
                        }
                        composable("signup") {
                            SignUpScreen(
                                onSignUpSuccess = {
                                    mainViewModel.handleSignUpSuccess()
                                },
                                onNavigateToLogin = { mainViewModel.handleNavigationToSignIn() },
                            )
                        }
                        composable("home") {
                            Text("Welcome to Home Screen!")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RootScreenContent(navController: NavHostController, mainViewModel: MainViewModel) {
    val user = FirebaseAuth.getInstance().currentUser
    val onboardingCompleted = mainViewModel.isOnboardingCompleted.collectAsState().value

    // No UI elements

    // Navigate based on the auth state and onboarding status
    LaunchedEffect(user, onboardingCompleted) {
        if (user != null) {
            // If the user is logged in, check onboarding completion
            if (onboardingCompleted) {
                mainViewModel.handleLoginSuccess()
            } else {
                // If onboarding is not completed, navigate to the onboarding screen
                navController.navigate("onboarding") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
        } else {
            // If the user is not logged in, navigate to the login screen
            mainViewModel.handleNavigationToSignIn()
        }
    }
}
