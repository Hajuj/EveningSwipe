package com.example.user_demo_postgres.utils.mapper

import com.example.user_demo_postgres.dto.GroupDTO
import com.example.user_demo_postgres.dto.TokenDto
import com.example.user_demo_postgres.entity.Group
import org.springframework.stereotype.Service

@Service
class GroupMapper: Mapper<GroupDTO, Group> {

    override fun fromEntity(entety: Group): GroupDTO = GroupDTO(entety.id,entety.name,entety.description,entety.owner)

    override fun toEntity(domain: GroupDTO): Group = Group(domain.id,domain.name,domain.description,domain.owner)
}