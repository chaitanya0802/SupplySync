package com.supplysync.android.ui.signUp

//for register request
data class SignUpRequest(
    val usertype: String,
    val username: String,   //phoneno
    val password: String,
    val email: String,
    val warehouse_name: String,
    val warehouseid: String,
    val size: String,
    val location: String,
)

//for register response
data class SignUpResponse(val message: String)