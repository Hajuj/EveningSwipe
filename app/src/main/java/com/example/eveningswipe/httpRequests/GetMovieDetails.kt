package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class GetMovieDetails(
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
){
    class Deserializer: ResponseDeserializable<Array<GetMovieDetails>> {
        override fun deserialize(content: String): Array<GetMovieDetails>? = Gson().fromJson(content, Array<GetMovieDetails>::class.java)
    }
}