package com.supplysync.android.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//to create singleton instance of api service
object RetrofitInstance {
    private const val BASE_URL = "http://192.168.29.91:8000/app/"

    private fun getClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor{chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder().method(originalRequest.method, originalRequest.body)

                val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("auth_token", null)

                if (token != null) {
                    requestBuilder.addHeader("Authorization", "Token $token")
                }


                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()
    }

    fun getApiService(context: Context): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getClient(context))     //The HTTP client used for requests.
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}