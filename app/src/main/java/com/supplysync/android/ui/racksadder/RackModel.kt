package com.supplysync.android.ui.racksadder


data class RackAddRequest(
    val racks_identifier :String,
    val section_identifier :String,
    val size  : String,
    var is_filled: Boolean,
    val product_id: String,
    val quantity: String
)

data class RackAddResponse(
    val racks_identifier :String,
    val section_identifier :String,
    val size  : String,
    var is_filled: Boolean,
    val product_id: String,
    val quantity: String
)


