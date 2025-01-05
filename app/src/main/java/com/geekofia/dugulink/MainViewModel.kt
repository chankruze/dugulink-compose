package com.geekofia.dugulink

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    // MutableStateFlow for managing the current navigation state
    private val _navigateTo = MutableStateFlow<String?>(null)
    val navigateTo: StateFlow<String?> get() = _navigateTo

    fun onFinishOnboarding() {
        // Set the navigation destination when onboarding is finished
        _navigateTo.value = "login" // or "home" depending on your logic
    }
}
