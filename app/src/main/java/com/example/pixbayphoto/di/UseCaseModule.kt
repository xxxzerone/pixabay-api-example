package com.example.pixbayphoto.di

import com.example.pixbayphoto.domain.repository.ItemRepository
import com.example.pixbayphoto.domain.usecase.GetItemById
import com.example.pixbayphoto.domain.usecase.GetItemsSortedByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetItemsSortedByIdUseCase(itemRepository: ItemRepository): GetItemsSortedByIdUseCase {
        return GetItemsSortedByIdUseCase(repository = itemRepository)
    }

    @Provides
    @Singleton
    fun provideGetItemByIdUseCase(itemRepository: ItemRepository): GetItemById {
        return GetItemById(repository = itemRepository)
    }
}