package com.example.user_demo_postgres.repository

import com.example.user_demo_postgres.entity.MovieRating
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
interface MovieRatingRepository: CrudRepository<MovieRating, Int> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update movie_rating set rating = rating +1 where movie_id = ?1 and filter_id = ?2")
    fun rateMovie(movie_id: String, filter_id: Int)

    @Query(nativeQuery = true, value = " select * from movie_rating where filter_id = ?1 order by rating desc" )
    fun getMovieRating(filter_id: Int):List<MovieRating>


}