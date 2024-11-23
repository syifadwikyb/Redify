package com.example.redify.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redify.data.local.LocalDatabase
import com.example.redify.data.local.entity.Book
import com.example.redify.data.remote.model.BookItem
import com.example.redify.data.remote.network.RetrofitClient
import com.example.redify.data.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel (private val application: Application) : AndroidViewModel(application) {
    private val repository: BookRepository = BookRepository(RetrofitClient.instance, LocalDatabase.getDatabase(application).getBookDao())

    private val _bookDetail = MutableLiveData<BookItem>()
    val bookDetail: MutableLiveData<BookItem> get() = _bookDetail

    private val _exception = MutableLiveData<Boolean>()
    val exception: MutableLiveData<Boolean> get() = _exception

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: MutableLiveData<Boolean> get() = _isSaved

    fun getBookDetail(id: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val detail = repository.getBookDetails(id)
                _bookDetail.postValue(detail)
                _exception.postValue(false)
            } catch (e: Exception) {
                _exception.postValue(true)
            }
        }
    }

    fun isBookSaved(id: String) {
        viewModelScope.launch (Dispatchers.IO) {
            val isExist = repository.isBookExist(id)
            if (isExist > 0) {
                _isSaved.postValue(true)
            } else {
                _isSaved.postValue(false)
            }
        }
    }

    fun saveBook(book: Book) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.insertBook(book)
        }
    }

    fun deleteBook(id: String) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteBook(id)
        }
    }

    fun clearException() {
        _exception.value = false
    }
}