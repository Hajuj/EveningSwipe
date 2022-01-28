package com.example.eveningswipe.ui.filmswipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SwipeViewModel: ViewModel() {
    val groupName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val movieTitle: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val movieText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val movieDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val movieVote: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val movieVoteCount: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}
