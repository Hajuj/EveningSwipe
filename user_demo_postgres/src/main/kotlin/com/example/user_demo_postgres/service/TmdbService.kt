package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.entity.TmdbMovieInfo

interface TmdbService {

     fun getMovieResource(id: String): TmdbMovieInfo?

}