package com.example.msp.backend.backendtry.web.rest

import com.example.msp.backend.backendtry.dto.MovieDTO
import com.example.msp.backend.backendtry.service.MovieService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MovieResource(
    private val movieService: MovieService
) {

    @PostMapping
    fun createMovie(@RequestBody movieDTO: MovieDTO): ResponseEntity<MovieDTO> {
        return ResponseEntity(movieService.createMovie(movieDTO), HttpStatus.CREATED)
    }

    @GetMapping
    fun getMovies(): ResponseEntity<List<MovieDTO>> =
        ResponseEntity.ok(movieService.getMovies())

    @GetMapping("/{id}")
    fun getMovie(@PathVariable id: Int) =
        ResponseEntity.ok(movieService.getMovie(id))

    @PutMapping
    fun updateMovie(@RequestBody movieDTO: MovieDTO): ResponseEntity<MovieDTO> =
        ResponseEntity.ok(movieService.updateMovie(movieDTO))

    @DeleteMapping("/{id}")
    fun deleteMovie(@PathVariable id: Int): ResponseEntity<Unit> =
        ResponseEntity(movieService.deleteMovie(id), HttpStatus.NO_CONTENT)

}