package com.example.eveningswipe.httpRequests.postRequests

import com.google.gson.annotations.SerializedName

data class CreateGroup2(
    @SerializedName("token") val token: TokenCG,
    @SerializedName("group") val group: Group
)
data class TokenCG(
    @SerializedName("token") val token : String
)
data class Group (

    @SerializedName("name") val name : String,
    @SerializedName("description") val description : String
)