package com.supplysync.android.ui.home

data class WarehouseDetails(
    val warehouse_name: String,
    val percent_filled: Float,
    val total_sections: Int,
    val total_racks: Int
)


data class SectionDetails(
    val percent_section_filled: Float,
    val total_empty_sections: Int,
    val total_filled_sections: Int
)

data class FilledsizeSectionidResponse(
    val section_id: Int,
    val size_filled: Float
)