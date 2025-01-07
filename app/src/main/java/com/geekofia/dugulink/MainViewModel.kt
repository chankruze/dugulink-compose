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

    // Track onboarding completion state
    private val _isOnboardingCompleted = MutableStateFlow(preferences.getOnboardingCompleted())
    val isOnboardingCompleted: StateFlow<Boolean> get() = _isOnboardingCompleted


    fun setOnboardingCompleted() {
        preferences.setOnboardingCompleted(true)
        _isOnboardingCompleted.value = true
        _navigateTo.value = "login"
    }
    fun handleLoginSuccess() {
        _navigateTo.value = "home" // Navigate to home on login success
    }

    fun handleSignUpSuccess() {
        _navigateTo.value = "home" // Navigate to home on signup success
    }

    fun handleNavigationToSignUp() {
        _navigateTo.value = "signup" // Navigate to signup screen
    }

    fun handleNavigationToSignIn() {
        _navigateTo.value = "signup" // Navigate to signup screen
    }
}
