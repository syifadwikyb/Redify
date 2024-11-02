package com.example.redify.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redify.data.remote.model.Data
import com.example.redify.data.repository.EventRepository
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: EventRepository) : ViewModel() {
    private val _homeVerHor = MutableLiveData<List<Data>>()
    val homeVerHor: LiveData<List<Data>> get() = _homeVerHor

    private val _exception = MutableLiveData<Boolean>()
    val exception: LiveData<Boolean> get() = _exception

    fun getVerHor() {
        viewModelScope.launch {
            try {
                val verticalEvents = repository.fetchVerEvents()
                _exception.value = false
            } catch (e: Exception) {
                _exception.value = true
            }
        }
    }

    fun clearException() {
        _exception.value = false
    }

    fun updateData(events: List<Data>) {
        _homeVerHor.value = events
    }
}