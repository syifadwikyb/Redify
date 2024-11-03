package com.example.redify.data.repository

import com.example.redify.data.local.dao.BookDao
import com.example.redify.data.local.entity.Book
import com.example.redify.data.remote.model.BookItem
import com.example.redify.data.remote.model.BooksResponse
import com.example.redify.data.remote.network.ApiService

class BookRepository(private val apiService: ApiService, private val bookDao: BookDao) {
    suspend fun getBooks(query: String): BooksResponse {
        return apiService.getBooks(query)
    }

    suspend fun getBookDetails(id: String): BookItem {
        return apiService.getBookDetails(id)
    }

    suspend fun getFavBooks():List<Book> {
        return bookDao.getAllBook()
    }

    suspend fun isBookExist(id: String):Int {
        return bookDao.isBookExist(id)
    }

    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun deleteBook(id: String) {
        bookDao.deleteBook(id)
    }
}