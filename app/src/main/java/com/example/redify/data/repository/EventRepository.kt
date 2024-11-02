package com.example.redify.data.repository

import com.example.redify.data.remote.model.Data
import com.example.redify.data.remote.network.ApiService

class EventRepository(private val apiService: ApiService) {
    suspend fun fetchVerEvents(): List<Data> {
        return apiService.getVerEvents()
    }
}