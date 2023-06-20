package com.example.pandaapplication.application

import android.app.Application
import com.example.pandaapplication.core.di.fragmentModule
import com.example.pandaapplication.core.di.networkModule
import com.example.pandaapplication.core.di.repositoryModule
import com.example.pandaapplication.core.di.viewModelModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(networkModule, repositoryModule, viewModelModule, fragmentModule))
        }


    }
}