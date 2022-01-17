package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.MovieRatingDTO
import com.example.user_demo_postgres.repository.MovieRatingRepository
import com.example.user_demo_postgres.utils.mapper.MovieRatingMapper
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class MovieRatingServiceImpl(
    private val movieRatingMapper: MovieRatingMapper,
    private val movieRatingRepository: MovieRatingRepository
) : MovieRatingService {

    override fun createMovieRating(movieRatingDTO: MovieRatingDTO): MovieRatingDTO {
        val rating = movieRatingMapper.toEntity(movieRatingDTO)
        movieRatingRepository.save(rating)
        return movieRatingDTO
    }
    @Transactional
    override fun rateMovie(movie_id: String, filter_id: Int) {
        val  rating = movieRatingRepository.rateMovie(movie_id,filter_id)

    }

    override fun getRating(filter_id: Int): List<MovieRatingDTO> {
        val rating = movieRatingRepository.getMovieRating(filter_id)
        return rating.map {
            movieRatingMapper.fromEntity(it)
        }
    }


}