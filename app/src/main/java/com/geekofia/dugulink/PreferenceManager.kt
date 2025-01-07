package com.geekofia.dugulink

import android.content.Context

class PreferencesManager(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("dugulink_prefs", Context.MODE_PRIVATE)

    fun getOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean("onboarding_completed", false)
    }

    fun setOnboardingCompleted(completed: Boolean) {
        sharedPreferences.edit().putBoolean("onboarding_completed", completed).apply()
    }
}
