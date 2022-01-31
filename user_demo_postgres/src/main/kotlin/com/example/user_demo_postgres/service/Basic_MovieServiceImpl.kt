package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.Basic_movieDTO
import com.example.user_demo_postgres.dto.GroupFilterDTO
import com.example.user_demo_postgres.exception.MovieException
import com.example.user_demo_postgres.repository.Basic_movieRepository
import com.example.user_demo_postgres.utils.mapper.Basic_movieMapper
import org.springframework.stereotype.Service

@Service
class Basic_MovieServiceImpl (
    private val basicMovierepository: Basic_movieRepository,
    private val basicMoviemapper: Basic_movieMapper

        ): Basic_MovieService {
    override fun getBasicMovie(id: String): Basic_movieDTO {
        val optinalMovie = basicMovierepository.findById(id)
        val movie = optinalMovie.orElseThrow{ MovieException("Movie with id $id is not valid") }
        return  basicMoviemapper.fromEntity(movie)

   }
    override fun getRandBasicMovies(): List<String> {
        return basicMovierepository.getRandBasicMovie()
    }

    override fun getMoveiByFilter(filterDTO: GroupFilterDTO): List<String>{
        val moveis = basicMovierepository.getMoviesByFilter(filterDTO.genre_1,filterDTO.max_year,filterDTO.min_year, filterDTO.rating, filterDTO.votes,filterDTO.max_runtime, filterDTO.size)
        return moveis
    }

    override fun getRandomMovies(size: Int): List<String> {
        return basicMovierepository.getRandomMovies(size)

    }

}