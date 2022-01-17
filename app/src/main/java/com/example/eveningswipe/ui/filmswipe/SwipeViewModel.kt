package com.example.eveningswipe.ui.filmswipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SwipeViewModel: ViewModel() {
    val movieTitle: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val movieText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}
