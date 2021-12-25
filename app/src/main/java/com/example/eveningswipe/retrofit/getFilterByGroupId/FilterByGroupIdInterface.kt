package com.example.eveningswipe.retrofit.getFilterByGroupId

import retrofit2.Call
import retrofit2.http.GET

interface FilterByGroupIdInterface {
    @GET("5")
    fun getFilterByGroupId(): Call<List<getFilterByGroupIdItem>>
}