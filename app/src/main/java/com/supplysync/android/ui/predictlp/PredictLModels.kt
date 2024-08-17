package com.supplysync.android.ui.predictlp

data class PredictionLRequest (
    val location: String,
    val target_date: String
)

data class PredictionLResponse (
    val predicted_demand: String
)
