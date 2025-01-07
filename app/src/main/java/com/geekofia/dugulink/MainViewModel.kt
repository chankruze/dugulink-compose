package com.geekofia.dugulink

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = PreferencesManager(application.applicationContext)

    // MutableStateFlow for managing the current navigation state
    private val _navigateTo = MutableStateFlow<String?>(null)
    val navigateTo: StateFlow<String?> get() = _navigateTo

    init {
        checkAppState()
    }

    private fun checkAppState() {
        val user = FirebaseAuth.getInstance().currentUser
        val hasCompletedOnboarding = preferences.getOnboardingCompleted()

        when {
            user != null -> _navigateTo.value = "home" // User is logged in
            !hasCompletedOnboarding -> _navigateTo.value = "onboarding" // Show onboarding
            else -> _navigateTo.value = "login" // Default to login
        }
    }

    fun setOnboardingCompleted() {
        preferences.setOnboardingCompleted(true)
    }
}
