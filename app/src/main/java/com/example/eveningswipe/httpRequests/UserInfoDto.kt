package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class UserInfoDto(
    val userName: String,
    val email: String,
    val groupId: List<Int>,
    val adminGroupId: List<Int>

){
    //User Deserializer
    class Deserializer : ResponseDeserializable<UserInfoDto> {
        override fun deserialize(content: String) = Gson().fromJson(content, UserInfoDto::class.java)
    }


}
