package com.example.pixbayphoto.domain.repository

import com.example.pixbayphoto.core.NetworkError
import com.example.pixbayphoto.core.Result
import com.example.pixbayphoto.domain.model.Pixabay

interface PixabayRepository {
    suspend fun loadPhoto(query: String): Result<List<Pixabay>, NetworkError>
}
