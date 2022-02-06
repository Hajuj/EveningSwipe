package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class PostMovieDetailsById(

    @SerializedName("token") val token: TokenDto,
    @SerializedName("movieId") val movieId: String
) {
    //User Deserializer
    class Deserializer : ResponseDeserializable<PostMovieDetailsById> {
        override fun deserialize(content: String) = Gson().fromJson(content, PostMovieDetailsById::class.java)
    }
}
