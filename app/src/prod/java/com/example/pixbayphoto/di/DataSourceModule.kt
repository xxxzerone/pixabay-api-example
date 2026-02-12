package com.example.pixbayphoto.di

import android.content.Context
import com.example.pixbayphoto.R
import com.example.pixbayphoto.data.datasource.DataSource
import com.example.pixbayphoto.data.datasource.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import jakarta.inject.Named
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    @Named("pixabay_api_key")
    fun providesPixabayApiKey(@ApplicationContext context: Context): String {
        return context.getString(R.string.pixabay_api_key)
    }

    @Singleton
    @Provides
    fun providesRemoteDataSource(
        @Named("pixabay_api_key") apiKey: String,
        httpClient: HttpClient
    ): DataSource {
        return RemoteDataSource(apiKey = apiKey, httpClient = httpClient)
    }
}