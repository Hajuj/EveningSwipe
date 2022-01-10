package com.example.eveningswipe.ui.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RatingResultViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is rating result Fragment"
    }
    val text: LiveData<String> = _text
}