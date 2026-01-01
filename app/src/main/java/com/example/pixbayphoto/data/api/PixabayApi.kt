package com.example.pixbayphoto.data.api

import com.example.pixbayphoto.data.dto.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("api/")
    suspend fun loadPixabayData(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("image_type") type: String,
    ): PixabayResponse
}
