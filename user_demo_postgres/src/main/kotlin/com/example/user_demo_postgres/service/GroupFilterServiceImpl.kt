package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.GroupFilterDTO
import com.example.user_demo_postgres.dto.MovieRatingDTO
import com.example.user_demo_postgres.exception.MovieException
import com.example.user_demo_postgres.repository.GroupFilterRepository
import com.example.user_demo_postgres.utils.mapper.GroupFilterMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service

class GroupFilterServiceImpl(
    private val groupFilterMapper: GroupFilterMapper,
    private val groupFilterRepository: GroupFilterRepository,
    private val groupService: GroupService,
    private val basicMovieservice: Basic_MovieService,
    private val ratingService: MovieRatingService
) : GroupFilterService {

    override fun createFilter(groupFilterDTO: GroupFilterDTO): GroupFilterDTO {
        val group = groupService.getGroup(groupFilterDTO.group_id)
        val movies = basicMovieservice.getMoveiByFilter(groupFilterDTO)
        groupFilterDTO.selection = movies
        val filter = groupFilterMapper.toEntity(groupFilterDTO)
        groupFilterRepository.save(filter)
        val saved_filter = groupFilterMapper.fromEntity(filter)
        for ( movie in movies){
            var rating = MovieRatingDTO( id = 0, movie_id = movie, filter_id = saved_filter.id, rating = 0)
            print(rating)
            ratingService.createMovieRating(rating)
        }

        return groupFilterMapper.fromEntity(filter)


    }

    override fun getMoviesByGroupId(id: Int): List<String> {
        val optinalfilter = groupFilterRepository.findById(id)
        val filter = optinalfilter.orElseThrow { MovieException("there is no filter with id: $id") }
        return groupFilterMapper.fromEntity(filter).selection
    }


    override fun getfilter(id: Int): GroupFilterDTO {
        val optinalfilter = groupFilterRepository.findById(id)
        val filter = optinalfilter.orElseThrow { MovieException("there is no group with id: $id") }
        return groupFilterMapper.fromEntity(filter)
    }

    override fun getFilterByGroupId(id: Int): List<GroupFilterDTO> {
        val group = groupService.getGroup(id)
        val optionalfilter = groupFilterRepository.getFilterByGroupId(id)
        return optionalfilter.map {
            groupFilterMapper.fromEntity(it)
        }
    }

    override fun  getGroupIdByFilter(filterId: Int): Int{
        val filter =groupFilterRepository.findByIdOrNull(filterId)

        filter?.let{
            return it.group_id
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    override fun createRandomFilter(groupFilterDTO: GroupFilterDTO): GroupFilterDTO {
        val group = groupService.getGroup(groupFilterDTO.group_id)
        val movies = basicMovieservice.getRandomMovies(groupFilterDTO.size)
        groupFilterDTO.selection = movies
        val filter = groupFilterMapper.toEntity(groupFilterDTO)
        groupFilterRepository.save(filter)
        val saved_filter = groupFilterMapper.fromEntity(filter)
        for ( movie in movies){
            var rating = MovieRatingDTO( id = 0, movie_id = movie, filter_id = saved_filter.id, rating = 0)
            print(rating)
            ratingService.createMovieRating(rating)
        }

        return groupFilterMapper.fromEntity(filter)



    }

}