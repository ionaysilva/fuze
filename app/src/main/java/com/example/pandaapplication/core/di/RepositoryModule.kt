package com.example.pandaapplication.core.di

import com.example.pandaapplication.repository.PandaRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PandaRepository(get()) }
}