package com.geekofia.dugulink.dashboard.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.geekofia.dugulink.dashboard.data.BottomNavItem

@Composable
fun DashboardScreen(
    onSignOut: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            DashboardBottomBar(navController)
        },
        content = {
            DashboardNavigation(
                navController = navController,
                onSignOut = onSignOut,
                modifier = Modifier.padding(it)
            )
        }
    )
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
    onSignOut: () -> Unit,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
    ) {
        composable(BottomNavItem.Home.route) {
            Text(text = "Home")
        }
        composable(BottomNavItem.Profile.route) {
            Text(text = "Profile")
        }
        composable(BottomNavItem.Settings.route) {
            Button(
                onClick = { onSignOut() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Out")
            }
        }
    }
}

@Preview
@Composable
fun DashboardPreview() {
    DashboardScreen(
        onSignOut = {
            Log.d("DashboardScreenPreview", "signout clicked")
        }
    )
}
