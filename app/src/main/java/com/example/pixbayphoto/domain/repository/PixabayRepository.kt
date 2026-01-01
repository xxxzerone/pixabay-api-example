package com.example.pixbayphoto.domain.repository

import com.example.pixbayphoto.core.NetworkError
import com.example.pixbayphoto.core.Result
import com.example.pixbayphoto.domain.model.Pixabay
import kotlinx.coroutines.flow.Flow

interface PixabayRepository {
    fun loadPhoto(query: String): Flow<Result<List<Pixabay>, NetworkError>>
}
