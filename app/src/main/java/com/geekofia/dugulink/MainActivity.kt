package com.geekofia.dugulink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geekofia.dugulink.onboarding.ui.OnboardingScreen
import com.geekofia.dugulink.ui.theme.DuguLinkTheme
import androidx.compose.runtime.collectAsState
import com.geekofia.dugulink.auth.ui.LoginScreen
import com.geekofia.dugulink.auth.ui.SignUpScreen
import com.geekofia.dugulink.dashboard.ui.DashboardScreen
import com.geekofia.dugulink.root.ui.RootScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val mainViewModel: MainViewModel = viewModel()
            val navigationDestination = mainViewModel.navigateTo.collectAsState().value

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

            DuguLinkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "root",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("root") {
                            RootScreen(navController, mainViewModel)
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
                        composable("dashboard") {
                            DashboardScreen(mainViewModel)
                        }
                    }
                }
            }
        }
    }
}
