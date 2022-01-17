package com.example.user_demo_postgres.utils.mapper

import com.example.user_demo_postgres.dto.MovieDTO
import com.example.user_demo_postgres.entity.Movie
import org.springframework.stereotype.Service

@Service
class MovieMapper: Mapper<MovieDTO, Movie> {
    override fun fromEntity(entety: Movie): MovieDTO = MovieDTO(

    entety.id,
    entety.title, entety.prime_title, entety.orig_title,
    entety.is_adult,
    entety.release_year,
    entety.run_time, entety.end_year,
    entety.genre_1,
    entety.genre_2,
    entety.genre_3
    )

    override fun toEntity(domain: MovieDTO): Movie = Movie(
        domain.id,
        domain.title, domain.prime_title, domain.orig_title,
        domain.is_adult,
        domain.release_year,
        domain.run_time, domain.end_year,
        domain.genre_1,
        domain.genre_2,
        domain.genre_3
    )


}