package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.GroupDTO
import com.example.user_demo_postgres.exception.MovieException
import com.example.user_demo_postgres.repository.GroupRepository
import com.example.user_demo_postgres.utils.mapper.GroupMapper
import org.springframework.stereotype.Service

@Service
class GroupServiceImpl (
    private val groupRepository: GroupRepository,
    private val groupMapper: GroupMapper
    ) : GroupService {

    override fun createGroup(groupDTO: GroupDTO): GroupDTO {
        val group = groupMapper.toEntity(groupDTO)
        groupRepository.save(group)
        return groupMapper.fromEntity(group)
    }

    override fun getGroup(id: Int): GroupDTO {
        val optinalGroup = groupRepository.findById(id)
        val group = optinalGroup.orElseThrow { MovieException("There is no group with id: $id") }
        return groupMapper.fromEntity(group)
    }

    override fun getOwner(id: Int): Int? {
       return groupRepository.getOwnerById(id).owner

    }

    override fun getGroupByAdmin(userId: Int): List<Int> = groupRepository.getGroupsByAdmin(userId)


}