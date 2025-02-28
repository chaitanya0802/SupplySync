package com.supplysync.android.ui.product

data class AddProductLotRequest(
    val warehouse_id: String,
    val rack: Int,
    val product_name: String,
    val supplier_name: String,
    val quantity: Int,
    val category: String,
    val price: Float,
    val lot_space: Float
)


data class AddProductLotResponse(
    val message: String
)