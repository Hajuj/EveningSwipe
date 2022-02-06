package com.example.msp.backend.backendtry.service

import com.example.msp.backend.backendtry.dto.MovieDTO
import com.example.msp.backend.backendtry.repository.MovieRepository
import com.example.msp.backend.backendtry.utils.exceptions.MovieException
import com.example.msp.backend.backendtry.utils.mapper.MovieMapper
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class MovieServiceImpl(
    private val movieRepo: MovieRepository,
    private val movieMapper: MovieMapper
) : MovieService {

    override fun createMovie(movieDTO: MovieDTO): MovieDTO {

        // if the value is not provided
        if (movieDTO.id != -1)
            throw IllegalArgumentException("check the ID, it should be null or -1")

        // generate the ID when saving the entity
        val movie = movieRepo.save(movieMapper.toEntity(movieDTO))

        return movieMapper.fromEntity(movie)
    }

    override fun getMovies(): List<MovieDTO> {
        val movieIterable = movieRepo.findAll()
        val movies = mutableListOf<MovieDTO>()

        movieIterable.forEach {
            movies.add(movieMapper.fromEntity(it))
        }

        if (movies.isEmpty())
            throw MovieException("the list is empty")

        return movies
    }

    override fun getMovie(id: Int): MovieDTO {
        val optionalMovie = movieRepo.findById(id)
        val movie = optionalMovie.orElseThrow { MovieException("This id $id for the movie doesn't exist") }

        return movieMapper.fromEntity(movie)
    }

    override fun updateMovie(movieDTO: MovieDTO): MovieDTO {
        val exists = movieRepo.existsById(movieDTO.id)

        if (!exists)
            throw MovieException("This id ${movieDTO.id} for the movie doesn't exist")

        if (movieDTO.rating == 0.0 || movieDTO.name == "default")
            throw MovieException("All objects of the movie are expected")

        movieRepo.save(movieMapper.toEntity(movieDTO))

        return movieDTO
    }

    override fun deleteMovie(id: Int) {
        val exists = movieRepo.existsById(id)

        if (!exists)
            throw MovieException("This id $id for the movie doesn't exist")

        movieRepo.deleteById(id)
    }
}