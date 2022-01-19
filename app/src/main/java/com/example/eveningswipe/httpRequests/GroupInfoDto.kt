package com.example.eveningswipe.httpRequests

import com.google.gson.annotations.SerializedName

data class GroupInfoDto(

        val name: String,
        val member: List<Int>,
        val filter: List<Int>,
        @SerializedName("token") val token: TokenDto,
        @SerializedName("groupId") val groupid: Int
)
