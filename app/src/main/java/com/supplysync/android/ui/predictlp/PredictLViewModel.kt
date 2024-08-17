package com.supplysync.android.ui.predictlp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supplysync.android.network.RetrofitInstance
import com.supplysync.android.ui.predict.PredictionRequest
import kotlinx.coroutines.launch

class PredictLViewModel(application: Application) : AndroidViewModel(application) {
    private val _prediction = MutableLiveData<String>()
    val prediction : LiveData<String> = _prediction


    fun getPred(data : PredictionRequest){
        viewModelScope.launch {
            try{
                val context = getApplication<Application>().applicationContext
                val response = RetrofitInstance.getApiService(context).getPrediction(data)
                _prediction.postValue(response.predicted_demand)
            }
            catch (e: Exception){
                _prediction.postValue(e.toString())
            }
        }
    }

}