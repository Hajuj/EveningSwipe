package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class GetSwipeState(
    val nextMovie: Int
){
    //User Deserializer
    class Deserializer : ResponseDeserializable<GetSwipeState> {
        override fun deserialize(content: String) = Gson().fromJson(content, GetSwipeState::class.java)
    }
}