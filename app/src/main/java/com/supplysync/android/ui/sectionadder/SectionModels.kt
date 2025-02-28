package com.supplysync.android.ui.sectionadder

data class SectionAddRequest(
    val warehouse_id: String,
    val size:String
)

data class SectionAddResponse(
    val message:String
)

