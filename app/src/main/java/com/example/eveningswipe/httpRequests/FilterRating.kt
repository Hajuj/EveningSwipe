package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class FilterRating (
        val id: Int,
        val movie_id: String,
        val filter_id: Int,
        val rating: Int,
){
    class Deserializer: ResponseDeserializable<Array<FilterRating>> {
        override fun deserialize(content: String): Array<FilterRating>? = Gson().fromJson(content, Array<FilterRating>::class.java)
    }
}