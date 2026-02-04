package com.example.pixbayphoto.di

import com.example.pixbayphoto.data.repository.MockItemRepository
import com.example.pixbayphoto.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindItemRepository(
        mockItemRepository: MockItemRepository
    ) : ItemRepository
}