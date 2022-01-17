package com.example.user_demo_postgres.entity

data class TmdbMovieInfo(
    val movie_results: List<MovieResult>,
    val person_results: List<Any>,
    val tv_episode_results: List<Any>,
    val tv_results: List<Any>,
    val tv_season_results: List<Any>
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


