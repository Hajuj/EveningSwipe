package com.example.eveningswipe.httpRequests.postRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class PostGroupInfo(
    val filter: List<Any>,
    val member: List<Int>,
    val name: String
) {
    //User Deserializer
    class Deserializer : ResponseDeserializable<PostGroupInfo> {
        override fun deserialize(content: String) = Gson().fromJson(content, PostGroupInfo::class.java)
    }
}
