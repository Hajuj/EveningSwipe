package com.example.user_demo_postgres.utils.mapper

import com.example.user_demo_postgres.dto.MovieRatingDTO
import com.example.user_demo_postgres.entity.MovieRating
import org.springframework.stereotype.Service

@Service
class MovieRatingMapper: Mapper<MovieRatingDTO, MovieRating> {
    override fun fromEntity(entety: MovieRating): MovieRatingDTO = MovieRatingDTO(
        entety.id,
        entety.movie_id,
        entety.filter_id,
        entety.rating
    )

    override fun toEntity(domain: MovieRatingDTO): MovieRating = MovieRating(
        domain.id,
        domain.movie_id,
        domain.filter_id,
        domain.rating
    )

}