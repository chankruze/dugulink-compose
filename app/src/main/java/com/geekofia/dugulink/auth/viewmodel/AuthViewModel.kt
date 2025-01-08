package com.geekofia.dugulink.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.geekofia.dugulink.auth.data.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    fun loginWithEmail(onSuccess: () -> Unit) {
        repository.loginWithEmail(_uiState.value.email, _uiState.value.password) { success, error ->
            if (success) {
                onSuccess()
            } else {
                // Handle error
                println(error)
            }
        }
    }

    fun signUpWithEmail(onSuccess: () -> Unit) {
        repository.signUpWithEmail(
            _uiState.value.email,
            _uiState.value.password
        ) { success, error ->
            if (success) {
                onSuccess()
            } else {
                // Handle error
                println(error)
            }
        }
    }

    fun loginWithGoogle(idToken: String, onSuccess: () -> Unit) {
        // Firebase Authentication Logic
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    // Handle failure
                    task.exception?.printStackTrace()
                }
            }
    }
}

data class AuthUiState(
    val email: String = "",
    val password: String = ""
)
