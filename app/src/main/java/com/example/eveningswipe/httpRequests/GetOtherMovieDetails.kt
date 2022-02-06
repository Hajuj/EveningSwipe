package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class GetOtherMovieDetails(
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
) {
    //User Deserializer
    class Deserializer : ResponseDeserializable<GetOtherMovieDetails> {
        override fun deserialize(content: String) = Gson().fromJson(content, GetOtherMovieDetails::class.java)
    }
}