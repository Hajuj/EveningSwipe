package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.MovieRatingDTO

interface MovieRatingService {

    fun createMovieRating(movieRatingDTO: MovieRatingDTO): MovieRatingDTO

    fun rateMovie(movie_id:String, filter_id: Int)

    fun getRating(filter_id: Int): List<MovieRatingDTO>
}