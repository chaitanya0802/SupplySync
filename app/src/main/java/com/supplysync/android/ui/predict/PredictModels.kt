package com.supplysync.android.ui.predict

//data class PredictionRequest (
//    val target_date: String,
//    val product_code: Int
//)

data class PredictionResponse(
    val predictions: List<PredictionItem>
)

data class PredictionItem(
    val date: String,
    val predicted_order_quantity: Float,
    val estimated_warehouse_space_sqft: Float
)
data class PredictionDetails(
    val predicted_order_quantity: Float,
    val estimated_warehouse_space_sqft: Float
)
