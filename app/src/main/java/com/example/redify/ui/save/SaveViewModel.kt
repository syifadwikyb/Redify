package com.example.redify.ui.save

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.redify.data.local.LocalDatabase
import com.example.redify.data.local.entity.Book
import com.example.redify.data.remote.network.RetrofitClient
import com.example.redify.data.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaveViewModel (private val application: Application): AndroidViewModel(application) {

    private val repository: BookRepository = BookRepository(RetrofitClient.instance, LocalDatabase.getDatabase(application).getBookDao())

    private val _favBook = MutableLiveData<List<Book>>()
    val favBook: LiveData<List<Book>> get() = _favBook

    fun getFavBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favBooks = repository.getFavBooks()
                _favBook.postValue(favBooks)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}