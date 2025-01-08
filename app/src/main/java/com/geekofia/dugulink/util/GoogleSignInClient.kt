package com.geekofia.dugulink.util

import android.app.Activity
import com.geekofia.dugulink.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

fun getGoogleSignInClient(activity: Activity): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(R.string.default_web_client_id)) // From google-services.json
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(activity, gso)
}
