package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class GetFilterByGroupId1 : ArrayList<GetFilterByGroupId2>()

data class GetFilterByGroupId2 (

    val id: Int,
    val genre_1: String,
    val genre_2: String,
    val genre_3: String,
    val min_year: Int,
    val max_year: Int,
    val rating: Float,
    val votes: Int,
    val max_runtime: Int,
    val size: Int,
    val group_id: Int,
    val selection: ArrayList<String>
    ){
        class Deserializer: ResponseDeserializable<Array<GetFilterByGroupId2>> {
            override fun deserialize(content: String): Array<GetFilterByGroupId2>? = Gson().fromJson(content, Array<GetFilterByGroupId2>::class.java)
        }
}