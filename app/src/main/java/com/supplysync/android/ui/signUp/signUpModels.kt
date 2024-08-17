package com.supplysync.android.ui.signUp

//for register request
data class SignUpRequest(
    val username: String,   //phoneno
    val password: String,
    val email: String,
    val role: String        //if user or merchant
)

//for register response
data class SignUpResponse(val message: String)