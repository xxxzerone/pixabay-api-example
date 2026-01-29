package com.example.pixbayphoto.di

import android.content.Context
import com.example.pixbayphoto.R
import com.example.pixbayphoto.data.datasource.DataSource
import com.example.pixbayphoto.data.datasource.DefaultDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun providesApiKey(@ApplicationContext context: Context): String {
        return context.getString(R.string.pixabay_api_key)
    }

    @Singleton
    @Provides
    fun providesDataSource(httpClient: HttpClient, apiKey: String): DataSource {
        return DefaultDataSource(httpClient = httpClient, apiKey = apiKey)
    }
}