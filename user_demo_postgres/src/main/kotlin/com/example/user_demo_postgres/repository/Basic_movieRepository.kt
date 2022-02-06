package com.example.user_demo_postgres.repository

import com.example.user_demo_postgres.entity.Basic_movie
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.data.repository.CrudRepository

@Repository
interface Basic_movieRepository: CrudRepository<Basic_movie, String> {

    @Query(nativeQuery = true, value = "select id from basic_movie where title like 'movie' and is_adult = '0' and runtime > '60' and rel_date > '1990' order by random() limit 25 ")
    fun getRandBasicMovie(): List<String>

    @Query(nativeQuery = true, value= "select id from basic_movie where" +
            "    (genre_1 = ?1 or genre_2 = ?1 or genre_3 = ?1) and" +
            "                                 (rel_date >= ?2) and" +
            "                                 rel_date <= ?3 and" +
            "                                 rating >= ?4 and" +
            "                                 votes >= ?5 and" +
            "                                 runtime <= ?6 limit ?7" )
    fun getMoviesByFilter(genre_1: String, min_year: Int, max_year: Int, rating: Double, votes: Int, max_runtime: Int , size: Int):List<String>


    @Query(nativeQuery = true, value = "select id from basic_movie where rating >= 6 and votes >= 800 order by  random() limit ?1 ")
    fun getRandomMovies(size: Int) : List<String>
}