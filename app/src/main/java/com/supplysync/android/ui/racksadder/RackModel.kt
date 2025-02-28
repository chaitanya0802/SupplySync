package com.supplysync.android.ui.racksadder


data class RackAddRequest(
    val warehouse_id: String,
    val section : String,
    val size  : String,
)

data class RackAddResponse(
    val message: String
)


