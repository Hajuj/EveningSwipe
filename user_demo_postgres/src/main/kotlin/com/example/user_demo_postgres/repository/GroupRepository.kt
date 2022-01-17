package com.example.user_demo_postgres.repository

import com.example.user_demo_postgres.entity.Group
import com.example.user_demo_postgres.entity.GroupFilter
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface GroupRepository: CrudRepository<Group, Int> {

    @Query("Select * from gruppe where id = ?1", nativeQuery = true)
    fun getOwnerById(idx: Int): Group

    @Query("Select id from gruppe where owner = ?1", nativeQuery = true)
    fun getGroupsByAdmin(userInt: Int): List<Int>



}