package com.example.user_demo_postgres.repository

import com.example.user_demo_postgres.entity.Movie
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository: CrudRepository<Movie, Int> {

    @Query ("select m from Movie as m")
    fun getAllMovies(): List<Movie>
}
