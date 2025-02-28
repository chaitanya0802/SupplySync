package com.supplysync.android.ui.predict

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supplysync.android.network.RetrofitInstance
import kotlinx.coroutines.launch

//class PredictViewModel(application: Application) : AndroidViewModel(application){
//
//
//    private val _predictions = MutableLiveData<LinkedHashMap<String, PredictionDetails>>()
//    val predictions: LiveData<LinkedHashMap<String, PredictionDetails>> = _predictions
//
//    fun getPred(date: String, productId: Int) {
//        viewModelScope.launch {
//            try {
//                val context = getApplication<Application>().applicationContext
//                val response = RetrofitInstance.getApiService(context).getPrediction(date, productId)
//
//                // Update LiveData with the response
//                _predictions.postValue(response.predictions)
//            } catch (e: Exception) {
//                e.printStackTrace()
//
//                // Create a LinkedHashMap with error details
//                val errorMap = LinkedHashMap<String, PredictionDetails>().apply {
//                    put(
//                        "Error",
//                        PredictionDetails(
//                            predicted_order_quantity = -1f, // Use -1 or other invalid value to indicate an error
//                            estimated_warehouse_space_sqft = -1f // Use -1 or other invalid value to indicate an error
//                        )
//                    )
//                }
//
//                // Post the error map
//                _predictions.postValue(errorMap)
//            }
//        }
//    }
//}