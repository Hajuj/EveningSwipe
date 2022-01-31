package com.example.user_demo_postgres.repository

import com.example.user_demo_postgres.entity.RatingUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface RatingUserRepository: JpaRepository<RatingUser, Int> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update rating_user set rating = rating +1 where filter_id = ?1 and user_id = ?2")
    fun increaseUserRating(filterId: Int , userId: Int)

    @Query(nativeQuery = true , value = "select rating from rating_user where user_id= ?1 and filter_id = ?2")
    fun getSwipeState(userId: Int, filterId: Int): Int

}