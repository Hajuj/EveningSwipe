package com.example.user_demo_postgres.utils.mapper

import com.example.user_demo_postgres.dto.Basic_movieDTO
import com.example.user_demo_postgres.entity.Basic_movie
import org.springframework.stereotype.Service

@Service
class Basic_movieMapper: Mapper<Basic_movieDTO, Basic_movie> {

    override fun fromEntity(entety: Basic_movie) = Basic_movieDTO(

        entety.id,
        entety.title,
        entety.prime_title,
        entety.orig_title,
        entety.is_adult,
        entety.rel_date,
        entety.end_date,
        entety.runtime,
        entety.genre_1,
        entety.genre_1,
        entety.genre_3,
        entety.rating,
        entety.votes

    )


    override fun toEntity(domain: Basic_movieDTO): Basic_movie = Basic_movie(

        domain.id,
        domain.title,
        domain.prime_title,
        domain.orig_title,
        domain.is_adult,
        domain.rel_date,
        domain.end_date,
        domain.runtime,
        domain.genre_1,
        domain.genre_1,
        domain.genre_3,
        domain.rating,
        domain.votes

    )

}