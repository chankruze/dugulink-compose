package com.geekofia.dugulink.navigation

sealed class PublicNavScreen(val route: String) {
    data object Onboarding : PublicNavScreen("onboarding")
    data object Login : PublicNavScreen("login")
    data object SignUp : PublicNavScreen("signup")
}

sealed class ProtectedNavScreen(val route: String) {
    data object Dashboard : ProtectedNavScreen("dashboard")
    data object Clients : ProtectedNavScreen("clients")
    data object Devices : ProtectedNavScreen("devices")
    data object Settings : ProtectedNavScreen("settings")
}
