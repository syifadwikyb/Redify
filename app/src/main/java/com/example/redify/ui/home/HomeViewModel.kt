package com.example.redify.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.redify.data.local.LocalDatabase
import com.example.redify.data.remote.model.BookItem
import com.example.redify.data.remote.network.RetrofitClient
import com.example.redify.data.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (private val application: Application) : AndroidViewModel(application) {
    private val repository: BookRepository = BookRepository(RetrofitClient.instance, LocalDatabase.getDatabase(application).getBookDao())

    private val _homeBook = MutableLiveData<List<BookItem>>()
    val homeBook: LiveData<List<BookItem>> get() = _homeBook

    private val _exception = MutableLiveData<Boolean>()
    val exception: LiveData<Boolean> get() = _exception

    fun getBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val itemBook = repository.getBooks("*").items
                _homeBook.postValue(itemBook)
                _exception.postValue(false)
            } catch (e: Exception) {
                _exception.postValue(true)
            }
        }
    }

    fun clearException() {
        _exception.value = false
    }
}