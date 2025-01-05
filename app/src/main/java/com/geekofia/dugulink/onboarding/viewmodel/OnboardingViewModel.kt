package com.geekofia.dugulink.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OnboardingViewModel : ViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> get() = _currentPage

    fun nextPage() {
        _currentPage.value++
    }

    fun previousPage() {
        _currentPage.value = (_currentPage.value - 1).coerceAtLeast(0)
    }
}
