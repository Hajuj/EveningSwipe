package com.example.user_demo_postgres.utils.mapper

import com.example.user_demo_postgres.dto.GroupFilterDTO
import com.example.user_demo_postgres.entity.GroupFilter
import org.springframework.stereotype.Service

@Service
class GroupFilterMapper: Mapper<GroupFilterDTO, GroupFilter> {
    override fun fromEntity(entety: GroupFilter): GroupFilterDTO = GroupFilterDTO(
        entety.id,
        entety.name,
        entety.genre_1,
        entety.genre_2,
        entety.genre_3,
        entety.min_year,
        entety.max_year,
        entety.rating,
        entety.votes,
        entety.max_runtime,
        entety.size,
        entety.group_id,
        entety.selection.split(",").map{it.trim()}
    )

    override fun toEntity(domain: GroupFilterDTO): GroupFilter = GroupFilter(
        domain.id,
        domain.name,
        domain.genre_1,
        domain.genre_2,
        domain.genre_3,
        domain.min_year,
        domain.max_year,
        domain.rating,
        domain.votes,
        domain.max_runtime,
        domain.size,
        domain.group_id,
        domain.selection.joinToString(separator = ",")
    )


}