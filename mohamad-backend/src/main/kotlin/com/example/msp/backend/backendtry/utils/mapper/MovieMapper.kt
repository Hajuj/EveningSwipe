package com.example.msp.backend.backendtry.utils.mapper

import com.example.msp.backend.backendtry.dto.MovieDTO
import com.example.msp.backend.backendtry.entity.MovieEntity
import org.springframework.stereotype.Service

@Service
class MovieMapper : Mapper<MovieDTO, MovieEntity> {

    override fun fromEntity(entity: MovieEntity): MovieDTO = MovieDTO(
        entity.id,
        entity.name,
        entity.rating
    )

    override fun toEntity(domain: MovieDTO): MovieEntity = MovieEntity(
        domain.id,
        domain.name,
        domain.rating
    )


}