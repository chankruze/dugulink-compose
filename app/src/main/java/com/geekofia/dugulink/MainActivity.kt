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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuguLinkTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = viewModel()
                val navigationDestination = mainViewModel.navigateTo.collectAsState().value

                // Observe navigation events
                LaunchedEffect(navigationDestination) {
                    navigationDestination?.let {
                        navController.navigate(it) // Navigate to the appropriate screen
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "onboarding",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("onboarding") {
                            OnboardingScreen(onFinish = {
                                // Call ViewModel method
                                mainViewModel.onFinishOnboarding()
                            })
                        }
                        // TODO
//                        composable("login") {
//                            LoginScreen(navController = navController)
//                        }
//                        composable("signup") {
//                            SignUpScreen(navController = navController)
//                        }
//                        composable("home") {
//                            HomeScreen()
//                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DuguLinkTheme {
        Greeting("Android")
    }
}
