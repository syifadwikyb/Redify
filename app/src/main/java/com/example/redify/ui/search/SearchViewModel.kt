package com.example.redify.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redify.data.local.LocalDatabase
import com.example.redify.data.remote.model.BookItem
import com.example.redify.data.remote.network.RetrofitClient
import com.example.redify.data.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BookRepository = BookRepository(RetrofitClient.instance, LocalDatabase.getDatabase(application).getBookDao())

    private val _searchBook = MutableLiveData<List<BookItem>>()
    val searchBook: LiveData<List<BookItem>> get() = _searchBook

    private val _exception = MutableLiveData<Boolean>()
    val exception: LiveData<Boolean> get() = _exception

    fun getBookByKeyword(keyword: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val books = repository.getBooks(keyword)
                if (books.totalItems > 0) {
                    _searchBook.postValue(books.items)
                } else {
                    _searchBook.postValue(listOf())
                }
            } catch (e: Exception) {
                _exception.postValue(true)
            }
        }
    }

    fun clearException() {
        _exception.value = false
    }
}