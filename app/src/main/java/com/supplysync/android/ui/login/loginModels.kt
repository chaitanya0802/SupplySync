package com.supplysync.android.ui.login

//for login request
data class LoginRequest(
    val username: String,   //phoneno
    val password: String
)

// for LoginResponse
data class LoginResponse(
    val message: String,
    val token: String
)
