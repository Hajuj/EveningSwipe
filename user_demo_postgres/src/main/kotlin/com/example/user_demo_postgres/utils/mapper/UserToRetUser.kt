package com.example.user_demo_postgres.utils.mapper

import com.example.user_demo_postgres.dto.RetUserEmNaDto
import com.example.user_demo_postgres.entity.UserApp

class UserToRetUser {

    fun userToRetUser(user: UserApp): RetUserEmNaDto {
        return RetUserEmNaDto(user.email, user.name)
    }
}