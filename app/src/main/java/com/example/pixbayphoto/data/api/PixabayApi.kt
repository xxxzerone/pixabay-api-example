package com.example.pixbayphoto.data.api

import com.example.pixbayphoto.data.dto.PixabayResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://pixabay.com/"

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

interface PixabayApi {
    @GET("api/")
    suspend fun loadPixabayData(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("image_type") type: String,
    ): PixabayResponse
}

val pixabayService = retrofit.create(PixabayApi::class.java)
