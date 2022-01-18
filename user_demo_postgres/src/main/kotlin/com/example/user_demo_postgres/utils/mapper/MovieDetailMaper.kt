package com.example.user_demo_postgres.utils.mapper

import com.example.user_demo_postgres.returnDto.RetMovieDetailsDto
import com.example.user_demo_postgres.entity.MovieResult
import org.springframework.stereotype.Service

@Service
class MovieDetailMaper {

    fun parseDmtbDetails(input: MovieResult): RetMovieDetailsDto {
        val output = RetMovieDetailsDto(
            input.original_title,
            input.overview,
            input.popularity,
            "https://image.tmdb.org/t/p/original" + input.poster_path,
            input.release_date,
            input.title,
            input.vote_average,
            input.vote_count


        )

        return output

    }
}

