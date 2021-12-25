package com.example.eveningswipe.retrofit.getMovieDetailsById

data class getMovieDetailsById(
    val movie_results: ArrayList<MovieResult>,
    val person_results: ArrayList<Any>,
    val tv_episode_results: ArrayList<Any>,
    val tv_results: ArrayList<Any>,
    val tv_season_results: ArrayList<Any>
)