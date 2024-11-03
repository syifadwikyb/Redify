package com.example.redify.data.remote.network

import com.example.redify.data.remote.model.BookItem
import com.example.redify.data.remote.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("volumes")
    suspend fun getBooks(@Query("q")query:String): BooksResponse

    @GET("volumes/{id}")
    suspend fun getBookDetails(@Path("id")id:String): BookItem
}