package com.geekofia.dugulink.feature.auth.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekofia.dugulink.feature.auth.domain.AuthResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _authResult = MutableStateFlow<AuthResult<Unit>>(AuthResult.Success())
    val authResult = _authResult.asStateFlow()

    fun signInWithEmail(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _authResult.value = AuthResult.Loading()
                auth.signInWithEmailAndPassword(email, password).await()
                _authResult.value = AuthResult.Success()
                onSuccess()
            } catch (e: Exception) {
                _authResult.value = AuthResult.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun signUpWithEmail(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _authResult.value = AuthResult.Loading()
                auth.createUserWithEmailAndPassword(email, password).await()
                _authResult.value = AuthResult.Success()
                onSuccess()
            } catch (e: Exception) {
                _authResult.value = AuthResult.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _authResult.value = AuthResult.Loading()
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).await()
                _authResult.value = AuthResult.Success()
                onSuccess()
            } catch (e: Exception) {
                _authResult.value = AuthResult.Error(e.message ?: "Google sign in failed")
            }
        }
    }

    fun signOut() {
        auth.signOut()
    }
}
