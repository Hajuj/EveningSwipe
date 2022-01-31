package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.GroupFilterDTO

interface GroupFilterService {

    fun createFilter(groupFilterDTO: GroupFilterDTO): GroupFilterDTO

    fun getfilter(id: Int ): GroupFilterDTO

    fun getFilterByGroupId(id: Int): List<GroupFilterDTO>


    fun getMoviesByGroupId(id: Int): List<String>

    fun getGroupIdByFilter(filterId: Int): Int

    fun createRandomFilter(groupFilterDTO: GroupFilterDTO): GroupFilterDTO
}