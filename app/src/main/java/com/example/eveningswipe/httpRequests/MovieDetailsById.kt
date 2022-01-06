package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

data class MovieDetailsById(
    val movie_results: ArrayList<MovieResult>,
    val person_results: ArrayList<Any>,
    val tv_episode_results: ArrayList<Any>,
    val tv_results: ArrayList<Any>,
    val tv_season_results: ArrayList<Any>
)
data class MovieResult(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
{
    class Deserializer: ResponseDeserializable<Array<MovieDetailsById>> {
        override fun deserialize(content: String): Array<MovieDetailsById>? = Gson().fromJson(content, Array<MovieDetailsById>::class.java)
    }
}