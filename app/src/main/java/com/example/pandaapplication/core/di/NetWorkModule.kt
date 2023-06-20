package com.example.pandaapplication.core.di

import com.example.pandaapplication.networking.AuthInterceptor
import com.example.pandaapplication.networking.provideForecastApi
import com.example.pandaapplication.networking.provideOkHttpClient
import com.example.pandaapplication.networking.provideRetrofit
import org.koin.dsl.module

val networkModule = module {
    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideForecastApi(get()) }
    single { provideRetrofit(get()) }
}