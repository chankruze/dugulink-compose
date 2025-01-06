package com.geekofia.dugulink.onboarding.data

import com.geekofia.dugulink.R
import com.geekofia.dugulink.onboarding.ui.model.OnboardingPage

fun getOnboardingPages(): List<OnboardingPage> {
    return listOf(
        OnboardingPage(
            title = "Welcome to DuguLink",
            description = "Control your electrical appliances remotely with ease.",
            imageRes = R.drawable.undraw_broadcast_gyxl
        ),
        OnboardingPage(
            title = "Connect Appliances",
            description = "Easily add and manage your electrical appliances.",
            imageRes = R.drawable.undraw_smart_home_9s59
        ),
        OnboardingPage(
            title = "Remote Control",
            description = "Seamlessly monitor and control connected appliances remotely from anywhere.",
            imageRes = R.drawable.undraw_internet_on_the_go_npa2
        )
    )
}
