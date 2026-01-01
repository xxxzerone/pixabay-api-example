package com.example.pixbayphoto.di

import com.example.pixbayphoto.ui.screen.detail.DetailViewModel
import com.example.pixbayphoto.ui.screen.main.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(pixabayRepository = get()) }

    viewModel { (id: Int) ->
        DetailViewModel(pixabayRepository = get(), id = id)
    }
}
