package com.example.user_demo_postgres.repository

import com.example.user_demo_postgres.entity.Group
import com.example.user_demo_postgres.entity.GroupUsers
import com.example.user_demo_postgres.entity.UserApp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface GroupUserRepository: JpaRepository<GroupUsers, Int> {

    @Query("Select * from group_users where group_id = ?1", nativeQuery = true)
    fun getGroupUserById(idx: Int): List<GroupUsers>?

    @Query("Select group_id from group_users where user_id = ?1", nativeQuery = true)
    fun getGroupsByUser(userId: Int): List<Int>
}