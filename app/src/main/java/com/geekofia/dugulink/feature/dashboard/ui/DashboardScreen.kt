package com.geekofia.dugulink.feature.dashboard.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.geekofia.dugulink.feature.auth.viewmodel.AuthViewModel
import com.geekofia.dugulink.feature.dashboard.data.BottomNavItem

@Composable
fun DashboardScreen(
    onSignOut: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            DashboardBottomBar(navController)
        }
    ) { padding ->
        DashboardNavigation(
            navController = navController,
            modifier = Modifier.padding(padding),
            onSignOut = onSignOut
        )
    }
}

@Composable
private fun DashboardBottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile,
        BottomNavItem.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun DashboardNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onSignOut: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            Text(text = "Home")
        }
        composable(BottomNavItem.Profile.route) {
            Text(text = "Profile")
        }
        composable(BottomNavItem.Settings.route) {
            TextButton(onClick = {
                onSignOut()
            }) {
                Text(text = "Sign Out")
            }
        }
    }
}
