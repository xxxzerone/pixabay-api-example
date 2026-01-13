package com.example.pixbayphoto.di

import com.example.pixbayphoto.data.repository.MockItemRepositoryImpl
import com.example.pixbayphoto.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideItemRepository(): ItemRepository {
        return MockItemRepositoryImpl()
    }
}