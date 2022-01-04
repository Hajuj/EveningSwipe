package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class CreateGroup (
        val id: Int,
        val name: String,
        val description: String,
){
    class Deserializer: ResponseDeserializable<Array<CreateGroup>> {
        override fun deserialize(content: String): Array<CreateGroup>? = Gson().fromJson(content, Array<CreateGroup>::class.java)
    }
}