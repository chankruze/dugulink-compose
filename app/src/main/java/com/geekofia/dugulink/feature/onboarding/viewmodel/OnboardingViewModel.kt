package com.geekofia.dugulink.feature.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekofia.dugulink.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    fun onOnboardingComplete(onNavigateToAuth: () -> Unit) {
        viewModelScope.launch {
            userPreferencesRepository.setOnboardingCompleted()
            onNavigateToAuth()
        }
    }
}
