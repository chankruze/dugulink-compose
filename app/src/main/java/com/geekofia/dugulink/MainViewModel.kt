package com.geekofia.dugulink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekofia.dugulink.data.UserPreferencesRepository
import com.geekofia.dugulink.navigation.NavScreen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _startDestination = MutableStateFlow<String?>(NavScreen.Onboarding.route)
    val startDestination: StateFlow<String?> = _startDestination

    var currentScreen: String? = null
        private set

    fun checkInitialDestination() {
        viewModelScope.launch {
            userPreferencesRepository.isOnboardingCompleted.collect { isCompleted ->
                _startDestination.value = when {
                    !isCompleted -> NavScreen.Onboarding.route
                    auth.currentUser != null -> NavScreen.Dashboard.route
                    else -> NavScreen.Login.route
                }
            }
        }
    }

    fun updateCurrentScreen(screen: String) {
        currentScreen = screen
    }
}
