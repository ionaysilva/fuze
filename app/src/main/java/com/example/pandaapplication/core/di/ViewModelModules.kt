package com.example.pandaapplication.core.di

import com.example.pandaapplication.fragments.match.MatchFragmentViewModel
import com.example.pandaapplication.fragments.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MatchFragmentViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}




