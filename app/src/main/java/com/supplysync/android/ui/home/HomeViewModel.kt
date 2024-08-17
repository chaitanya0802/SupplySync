package com.supplysync.android.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supplysync.android.network.RetrofitInstance
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _title1 = MutableLiveData<String>()
    val title1 : LiveData<String> = _title1
    private val _imageurl1 = MutableLiveData<String>()
    val imageurl1 :LiveData<String> = _imageurl1

    private val _title2 = MutableLiveData<String>()
    val title2 : LiveData<String> = _title2
    private val _imageurl2 = MutableLiveData<String>()
    val imageurl2 :LiveData<String> = _imageurl2

    private val _title3 = MutableLiveData<String>()
    val title3 : LiveData<String> = _title2
    private val _imageurl3 = MutableLiveData<String>()
    val imageurl3 :LiveData<String> = _imageurl3

    private val _title4 = MutableLiveData<String>()
    val title4 : LiveData<String> = _title4
    private val _imageurl4 = MutableLiveData<String>()
    val imageurl4 :LiveData<String> = _imageurl4

    private val _title5 = MutableLiveData<String>()
    val title5 : LiveData<String> = _title5
    private val _imageurl5 = MutableLiveData<String>()
    val imageurl5 :LiveData<String> = _imageurl5

    private val _title6 = MutableLiveData<String>()
    val title6 : LiveData<String> = _title6
    private val _imageurl6 = MutableLiveData<String>()
    val imageurl6 :LiveData<String> = _imageurl6


    fun getVizData(){
        viewModelScope.launch {
            try{
                val context = getApplication<Application>().applicationContext
                val response = RetrofitInstance.getApiService(context).getViz1()
                _title1.postValue(response.title)
                _imageurl1.postValue(response.imageurl)
            }
            catch (e: Exception){
                _title1.postValue("Error while loading")
                _imageurl1.postValue("error")
            }

            try{
                val context = getApplication<Application>().applicationContext
                val response = RetrofitInstance.getApiService(context).getViz2()
                _title2.postValue(response.title)
                _imageurl2.postValue(response.imageurl)
            }
            catch (e: Exception){
                _title2.postValue("Error while loading")
                _imageurl2.postValue("error")
            }

            try{
                val context = getApplication<Application>().applicationContext
                val response = RetrofitInstance.getApiService(context).getViz3()
                _title3.postValue(response.title)
                _imageurl3.postValue(response.imageurl)
            }
            catch (e: Exception){
                _title3.postValue("Error while loading")
                _imageurl3.postValue("error")
            }

            try{
                val context = getApplication<Application>().applicationContext
                val response = RetrofitInstance.getApiService(context).getViz4()
                _title4.postValue(response.title)
                _imageurl4.postValue(response.imageurl)
            }
            catch (e: Exception){
                _title4.postValue("Error while loading")
                _imageurl4.postValue("error")
            }

            try{
                val context = getApplication<Application>().applicationContext
                val response = RetrofitInstance.getApiService(context).getViz5()
                _title5.postValue(response.title)
                _imageurl5.postValue(response.imageurl)
            }
            catch (e: Exception){
                _title5.postValue("Error while loading")
                _imageurl5.postValue("error")
            }

            try{
                val context = getApplication<Application>().applicationContext
                val response = RetrofitInstance.getApiService(context).getViz6()
                _title6.postValue(response.title)
                _imageurl6.postValue(response.imageurl)
            }
            catch (e: Exception){
                _title6.postValue("Error while loading")
                _imageurl6.postValue("error")
            }


        }
    }
}