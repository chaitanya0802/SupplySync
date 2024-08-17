package com.supplysync.android.ui.sectionadder

data class SectionAddRequest(
    val section_identifier:String,
    val description: String,
    val total_racks: String
)

data class SectionAddResponse(
    val section_identifier:String,
    val description: String,
    val total_racks: String
)

