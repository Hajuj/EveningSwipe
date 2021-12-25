package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Todos (
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
    ){
        class Deserializer: ResponseDeserializable<Array<Todos>> {
            override fun deserialize(content: String): Array<Todos>? = Gson().fromJson(content, Array<Todos>::class.java)
        }
}