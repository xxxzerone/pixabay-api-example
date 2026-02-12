package com.example.pixbayphoto.di

import com.example.pixbayphoto.data.datasource.DataSource
import com.example.pixbayphoto.data.mapper.ItemMapper
import com.example.pixbayphoto.data.repository.ItemRepositoryImpl
import com.example.pixbayphoto.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesRemoteRepository(dataSource: DataSource, itemMapper: ItemMapper): ItemRepository {
        return ItemRepositoryImpl(dataSource = dataSource, itemMapper = itemMapper)
    }
}