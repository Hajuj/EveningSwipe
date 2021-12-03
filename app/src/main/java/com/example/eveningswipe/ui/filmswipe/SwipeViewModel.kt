package com.example.eveningswipe.ui.filmswipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SwipeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is swipe Fragment"
    }
    val text: LiveData<String> = _text
}