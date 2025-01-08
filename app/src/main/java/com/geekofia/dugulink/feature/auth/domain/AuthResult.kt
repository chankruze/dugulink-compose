package com.geekofia.dugulink.feature.auth.domain

sealed class AuthResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T? = null) : AuthResult<T>(data)
    class Error<T>(message: String) : AuthResult<T>(message = message)
    class Loading<T> : AuthResult<T>()
}
