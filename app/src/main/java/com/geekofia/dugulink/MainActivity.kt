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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geekofia.dugulink.onboarding.ui.OnboardingScreen
import com.geekofia.dugulink.ui.theme.DuguLinkTheme
import androidx.compose.runtime.collectAsState
import com.geekofia.dugulink.auth.ui.LoginScreen
import com.geekofia.dugulink.auth.ui.SignUpScreen

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
                        startDestination = "splash",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("onboarding") {
                            OnboardingScreen(onFinish = {
                                // Mark onboarding as completed
                                mainViewModel.setOnboardingCompleted()
                                // Navigate to login
                                navController.navigate("login") {
                                    // Optional: If you want to clear the back stack so the user cannot navigate back to the onboarding screen
                                    popUpTo("onboarding") { inclusive = true }
                                }
                            })
                        }
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate("home") {
                                        popUpTo("login") {
                                            inclusive = true
                                        }
                                    }
                                },
                                onNavigateToSignUp = { navController.navigate("signup") }
                            )
                        }
                        composable("signup") {
                            SignUpScreen(
                                onSignUpSuccess = {
                                    navController.navigate("home") {
                                        popUpTo("signup") {
                                            inclusive = true
                                        }
                                    }
                                },
                                onNavigateToLogin = { navController.navigate("login") },
                            )
                        }
                        composable("home") {
                            // Placeholder for home screen
                            Text("Welcome to Home Screen!")
                        }
                    }
                }
            }
        }
    }
}
