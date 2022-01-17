package com.example.user_demo_postgres.utils.mapper

interface Mapper<D, E>{

    fun fromEntity(entety: E):D
    fun toEntity(domain: D) : E
}