package com.geekofia.dugulink.onboarding.data

import com.geekofia.dugulink.R
import com.geekofia.dugulink.onboarding.ui.model.OnboardingPage

fun getOnboardingPages(): List<OnboardingPage> {
    return listOf(
        OnboardingPage(
            title = "Welcome to DuguLink",
            description = "Control your appliances remotely with ease.",
            imageRes = R.drawable.ic_launcher_foreground
        ),
        OnboardingPage(
            title = "Add Devices",
            description = "Easily add and manage your devices.",
            imageRes = R.drawable.ic_launcher_foreground
        ),
        OnboardingPage(
            title = "Stay Connected",
            description = "Seamlessly monitor and control from anywhere.",
            imageRes = R.drawable.ic_launcher_foreground
        )
    )
}
