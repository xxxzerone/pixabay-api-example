package com.example.pixbayphoto

import android.app.Application
import com.example.pixbayphoto.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PixabayApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PixabayApplication)
            modules(
                viewModelModule
            )
        }
    }
}