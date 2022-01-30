package com.example.eveningswipe.httpRequests.postRequests

import com.example.eveningswipe.httpRequests.GroupInfoDto
import com.example.eveningswipe.httpRequests.TokenDto
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class FindUserInfoDto(

    @SerializedName("token") val token: String,
    @SerializedName("search") val search: String
) {
    //User Deserializer
    class Deserializer : ResponseDeserializable<FindUserInfoDto> {
        override fun deserialize(content: String) = Gson().fromJson(content, FindUserInfoDto::class.java)
    }
}
