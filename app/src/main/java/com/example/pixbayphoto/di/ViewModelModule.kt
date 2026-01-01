package com.example.pixbayphoto.di

import com.example.pixbayphoto.ui.screen.detail.DetailViewModel
import com.example.pixbayphoto.ui.screen.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(androidContext()) }

    viewModel { (id: Int) ->
        DetailViewModel(
            context = androidContext(),
            id = id
        )
    }
}
