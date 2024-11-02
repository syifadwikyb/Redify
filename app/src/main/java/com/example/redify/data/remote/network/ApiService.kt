package com.example.redify.data.remote.network

import com.example.redify.data.remote.model.Data
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getVerEvents(): List<Data>
}