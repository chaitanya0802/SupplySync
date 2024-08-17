package com.supplysync.android.ui.predict

data class PredictionRequest (
    val product_code: String,
    val target_date: String
)

data class PredictionResponse (
    val predicted_demand: String
)

