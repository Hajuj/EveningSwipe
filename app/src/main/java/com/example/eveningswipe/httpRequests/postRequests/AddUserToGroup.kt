package com.example.eveningswipe.httpRequests.postRequests

import com.google.gson.annotations.SerializedName

data class AddUserToGroup(
    @SerializedName("token") val token: TokenAddUser,
    @SerializedName("group") val add: Add
)
data class TokenAddUser(
    @SerializedName("token") val token : String
)
data class Add (

    @SerializedName("groupId") val groupId : Int,
    @SerializedName("toAdd") val toAdd : String
)