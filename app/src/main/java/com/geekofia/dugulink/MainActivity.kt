package com.geekofia.dugulink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState
import com.geekofia.dugulink.feature.onboarding.ui.OnboardingScreen
import com.geekofia.dugulink.navigation.NavScreen
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.geekofia.dugulink.feature.auth.domain.AuthResult
import com.geekofia.dugulink.feature.auth.ui.LoginScreen
import com.geekofia.dugulink.feature.auth.ui.SignUpScreen
import com.geekofia.dugulink.feature.auth.viewmodel.AuthViewModel
import com.geekofia.dugulink.feature.dashboard.ui.DashboardScreen
import com.geekofia.dugulink.ui.theme.DuguLinkTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Splash screen API
        installSplashScreen()
        // Edge to edge display
        enableEdgeToEdge()

        // UIo
        setContent {
            val navController = rememberNavController()
            val startDestination by mainViewModel.startDestination.collectAsState()
            val authResult by authViewModel.authResult.collectAsState()

            LaunchedEffect(Unit) {
                mainViewModel.checkInitialDestination()
            }

            // Handle authentication state changes
            LaunchedEffect(authResult) {
                when (authResult) {
                    is AuthResult.Success -> {
                        navController.navigate(NavScreen.Dashboard.route) {
                            popUpTo(NavScreen.Login.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }

                    is AuthResult.Error -> {
                        navController.navigate(NavScreen.Login.route) {
                            popUpTo(NavScreen.Dashboard.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }

                    else -> {} // Handle loading or other states
                }
            }

            DuguLinkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (startDestination != null) {
                        startDestination?.let { startRoute ->
                            NavHost(
                                navController = navController,
                                startDestination = startRoute
                            ) {
                                composable(NavScreen.Onboarding.route) {
                                    OnboardingScreen(
                                        onNavigateToAuth = {
                                            navController.navigate(NavScreen.Login.route) {
                                                popUpTo(NavScreen.Onboarding.route) { inclusive = true }
                                            }
                                        }
                                    )
                                }

                                // Auth flow
                                composable(NavScreen.Login.route) {
                                    LoginScreen(
                                        onNavigateToSignUp = {
                                            navController.navigate(NavScreen.SignUp.route)
                                        },
                                        onNavigateToDashboard = {
                                            navController.navigate(NavScreen.Dashboard.route) {
                                                popUpTo(navController.graph.id) { inclusive = true }
                                            }
                                        }
                                    )
                                }
                                composable(NavScreen.SignUp.route) {
                                    SignUpScreen(
                                        onNavigateToLogin = {
                                            navController.popBackStack()
                                        },
                                        onNavigateToDashboard = {
                                            navController.navigate(NavScreen.Dashboard.route) {
                                                popUpTo(navController.graph.id) { inclusive = true }
                                            }
                                        }
                                    )
                                }

                                // Main app flow
                                composable(NavScreen.Dashboard.route) {
                                    DashboardScreen(
                                        onSignOut = {
                                            authViewModel.signOut()
                                            navController.navigate(NavScreen.Login.route) {
                                                popUpTo(navController.graph.id) { inclusive = true }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Handle back button press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (mainViewModel.currentScreen == NavScreen.Dashboard.route) {
                    finish()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}
