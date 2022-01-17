package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.GroupDTO

interface GroupService {

    fun createGroup(groupDTO: GroupDTO): GroupDTO

    fun getGroup(id: Int): GroupDTO

    fun getOwner(id: Int): Int?

    fun getGroupByAdmin(userId: Int): List<Int>
}