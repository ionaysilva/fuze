package com.example.pandaapplication.core.di

import com.example.pandaapplication.fragments.match.MatchFragment
import com.example.pandaapplication.fragments.details.DetailsFragment
import org.koin.dsl.module

val fragmentModule = module {
    factory { MatchFragment() }
    factory { DetailsFragment() }
}
