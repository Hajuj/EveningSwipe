package com.example.eveningswipe.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eveningswipe.httpRequests.HttpRequests

class HomeViewModel : ViewModel() {



    private val _text = MutableLiveData<String>().apply {
        if(!HttpRequests.checkifInitialized()) {
            value = "Hi!"
        } else {
            value = "Hi " + HttpRequests.responseUserInfo?.userName + "!"
        }
    }
    val text: LiveData<String> = _text

}