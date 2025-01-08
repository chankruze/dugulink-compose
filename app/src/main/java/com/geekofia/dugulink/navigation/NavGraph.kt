package com.geekofia.dugulink.navigation

sealed class NavScreen(val route: String) {
    data object Onboarding : NavScreen("onboarding")
    data object Login : NavScreen("login")
    data object SignUp : NavScreen("signup")
    data object Dashboard : NavScreen("dashboard")
}
