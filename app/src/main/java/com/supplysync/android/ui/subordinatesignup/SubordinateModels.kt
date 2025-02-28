package com.supplysync.android.ui.subordinatesignup

//for register request
data class SubordinateSignUpRequest(
    val usertype: String,
    val username: String,   //phoneno
    val password: String,
    val email: String,
    val warehouseid: String,
)

//for register response
data class SubordinateSignUpResponse(val message: String)