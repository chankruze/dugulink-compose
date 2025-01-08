package com.geekofia.dugulink.util

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

fun getGithubSignInClient(activity: Activity) {
    val firebaseAuth = FirebaseAuth.getInstance()

    // Creating OAuthProvider for GitHub
    val provider = OAuthProvider.newBuilder("github.com")
        .setScopes(listOf("user:email")) // Optional: define the permissions you need
        .build()


    // Start the sign-in flow using the provider
    firebaseAuth.startActivityForSignInWithProvider(activity, provider)
        .addOnSuccessListener { authResult ->
            // Handle success: user signed in successfully
            val user = authResult.user
            // You can access user info like user.displayName, user.email etc.
            // Continue with your app logic, e.g., navigate to the dashboard
        }
        .addOnFailureListener { exception ->
            // Handle failure and show Snack bar
            // You can log the error or display a message to the user
            val errorMessage = exception.message ?: "GitHub Sign-In failed"
            Log.d("GIT_HUB_LOGIN", errorMessage)
        }
}
