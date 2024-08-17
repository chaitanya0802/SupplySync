package com.supplysync.android.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supplysync.android.network.RetrofitInstance
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _loginResult = MutableLiveData<String>()
    val loginResult :LiveData<String> = _loginResult

    //login
    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            try{
                val context = getApplication<Application>().applicationContext
                val response = RetrofitInstance.getApiService(context).login(loginRequest)
                if (response.message == "success"){
                    TokenManager.saveToken(context, response.token)
                    _loginResult.postValue(response.message)
                }
                else{
                    _loginResult.postValue(response.message)
                }
            }
            catch (e: Exception){
                _loginResult.postValue(e.toString())
            }
        }
    }
}