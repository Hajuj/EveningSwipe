package com.example.eveningswipe.httpRequests.postRequests

import com.google.gson.annotations.SerializedName

data class RegisterUser(
        @SerializedName("name") val name: String,
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String
)