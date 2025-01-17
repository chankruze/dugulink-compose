package com.geekofia.dugulink

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geekofia.dugulink.auth.ui.LoginScreen
import com.geekofia.dugulink.auth.ui.SignUpScreen
import com.geekofia.dugulink.onboarding.ui.OnboardingScreen
import com.geekofia.dugulink.root.ui.RootScreen
import com.geekofia.dugulink.ui.theme.DuguLinkTheme

class PublicActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
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
                            RootScreen(
                                onLoginSuccess = {
                                    navController.context.startActivity(
                                        Intent(
                                            navController.context,
                                            MainActivity::class.java
                                        )
                                    )
                                    finish()
                                },
                                onNavigateToOnboarding = {
                                    navController.navigate("onboarding") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                    }
                                },
                                mainViewModel = mainViewModel
                            )
                        }
                        composable("onboarding") {
                            OnboardingScreen(onFinish = {
                                mainViewModel.setOnboardingCompleted()
                            })
                        }
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = {
                                    startActivity(
                                        Intent(
                                            navController.context,
                                            MainActivity::class.java
                                        )
                                    )
                                    finish()
                                },
                                onNavigateToSignUp = { mainViewModel.handleNavigationToSignUp() }
                            )
                        }
                        composable("signup") {
                            SignUpScreen(
                                onSignUpSuccess = {
                                    startActivity(
                                        Intent(
                                            navController.context,
                                            MainActivity::class.java
                                        )
                                    )
                                    finish()
                                },
                                onNavigateToLogin = { mainViewModel.handleNavigationToSignIn() },
                            )
                        }
                    }
                }
            }
        }
    }
}
