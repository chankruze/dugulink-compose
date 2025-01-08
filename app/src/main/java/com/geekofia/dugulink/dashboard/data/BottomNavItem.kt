package com.geekofia.dugulink.dashboard.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(
        route = "home",
        title = "Home",
        icon = Icons.Filled.Home
    )

    data object Profile : BottomNavItem(
        route = "profile",
        title = "Profile",
        icon = Icons.Filled.Person
    )

    data object Settings : BottomNavItem(
        route = "settings",
        title = "Settings",
        icon = Icons.Filled.Settings
    )
}
