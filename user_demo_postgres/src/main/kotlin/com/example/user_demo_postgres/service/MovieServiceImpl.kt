package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.MovieDTO
import com.example.user_demo_postgres.exception.MovieException
import com.example.user_demo_postgres.repository.MovieRepository
import com.example.user_demo_postgres.utils.mapper.MovieMapper
import org.springframework.stereotype.Service

@Service
class MovieServiceImpl (
    private val movieRepository: MovieRepository,
    private  val movieMapper: MovieMapper
    ): MovieService {
    override fun createMovie(movieDTO: MovieDTO): MovieDTO {
        val movie = movieMapper.toEntity(movieDTO)
        movieRepository.save(movie)
        return movieDTO
    }

    override fun getMovies(): List<MovieDTO> {
        val movies = movieRepository.getAllMovies()
         return movies.map {
             movieMapper.fromEntity(it)

        }



    }

    override fun getMovie(id: Int): MovieDTO {
        val optinalMovie = movieRepository.findById(id)
        val movie = optinalMovie.orElseThrow{ MovieException("Movie with id $id is not valid") }
        return  movieMapper.fromEntity(movie)
    }
}