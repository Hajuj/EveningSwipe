package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class TokenDto(
    val token: String
) {
    //User Deserializer
    class Deserializer : ResponseDeserializable<TokenDto> {
        override fun deserialize(content: String) = Gson().fromJson(content, TokenDto::class.java)
    }
}
