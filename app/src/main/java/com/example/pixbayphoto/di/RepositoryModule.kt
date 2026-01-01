package com.example.pixbayphoto.di

import com.example.pixbayphoto.data.repository.PixabayRepositoryImpl
import com.example.pixbayphoto.domain.repository.PixabayRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<PixabayRepository> { PixabayRepositoryImpl(pixabayApi = get(), context = androidContext()) }
}
