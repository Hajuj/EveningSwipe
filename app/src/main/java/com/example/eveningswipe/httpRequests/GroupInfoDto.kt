package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class GroupInfoDto(

        //val name: String,
        @SerializedName("token") val token: TokenDto,
        @SerializedName("groupId") val groupid: Int
) {
    //User Deserializer
    class Deserializer : ResponseDeserializable<GroupInfoDto> {
        override fun deserialize(content: String) = Gson().fromJson(content, GroupInfoDto::class.java)
    }
}
