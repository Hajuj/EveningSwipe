package com.example.user_demo_postgres.repository

import com.example.user_demo_postgres.entity.GroupFilter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Qualifier("idx")
@Repository
interface GroupFilterRepository: CrudRepository<GroupFilter, Int> {

    @Query("Select * from filter where group_id = ?1", nativeQuery = true)
    fun getFilterByGroupId(idx: Int): List<GroupFilter>


}
