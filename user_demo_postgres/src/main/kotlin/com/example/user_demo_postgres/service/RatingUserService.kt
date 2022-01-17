package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.entity.RatingUser
import com.example.user_demo_postgres.repository.RatingUserRepository
import org.springframework.stereotype.Service

@Service
class RatingUserService(
    private val ratingUserRepositoy: RatingUserRepository
) {

    fun rateMovieInFilter(filterId: Int, userId: Int){
        ratingUserRepositoy.increaseUserRating(filterId, userId)


    }

    fun createRatinUser(filterId : Int , userId: Int){

        ratingUserRepositoy.save(RatingUser(0 ,filterId, userId, 0))
    }

}

