package com.example.eveningswipe.httpRequests.postRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class FindUserDto(
    val email: String,
    val name: String
) {
    //User Deserializer
    class Deserializer : ResponseDeserializable<FindUserDto> {
        override fun deserialize(content: String) = Gson().fromJson(content, FindUserDto::class.java)
    }
}
