package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.MovieDTO

interface MovieService {

    fun createMovie(movieDTO: MovieDTO): MovieDTO

    fun getMovies():List<MovieDTO>

    fun getMovie(id: Int): MovieDTO
}