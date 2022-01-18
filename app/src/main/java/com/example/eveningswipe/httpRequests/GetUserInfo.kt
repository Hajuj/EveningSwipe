package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class GetUserInfo (
        val name: String
){
    class Deserializer: ResponseDeserializable<Array<GetUserInfo>> {
        override fun deserialize(content: String): Array<GetUserInfo>? = Gson().fromJson(content, Array<GetUserInfo>::class.java)
    }
}