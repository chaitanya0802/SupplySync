package com.supplysync.android.ui.subordinatesignup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supplysync.android.network.RetrofitInstance
import kotlinx.coroutines.launch

class SubordinateSignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val _signUpResult = MutableLiveData<String>()
    val signUpResult : LiveData<String> = _signUpResult

    //to signup user
    fun signUpUser( newSignUpRequest: SubordinateSignUpRequest){
        viewModelScope.launch {
            try {
                val context = getApplication<Application>().applicationContext
                //phonenumber is treated as username at backend
                val response = RetrofitInstance.getApiService(context).subordinatesignUp(newSignUpRequest)
                _signUpResult.postValue(response.message)
            }
            catch (e: Exception){
                _signUpResult.postValue(e.toString())
            }
        }

    }

}