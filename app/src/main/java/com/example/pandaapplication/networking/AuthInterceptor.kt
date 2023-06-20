package com.example.pandaapplication.networking

import com.example.pandaapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Accept" , "application/json" )
            .addHeader("Authorization", "Bearer " + BuildConfig.API_TOKEN)
            .build()
        return chain.proceed(newRequest)
    }
}