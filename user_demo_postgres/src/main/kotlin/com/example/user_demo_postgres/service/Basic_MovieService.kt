package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.Basic_movieDTO
import com.example.user_demo_postgres.dto.GroupFilterDTO


interface Basic_MovieService {
     fun getBasicMovie (id: String): Basic_movieDTO

     fun getRandBasicMovies(): List<String>

     fun getMoveiByFilter(filterDTO: GroupFilterDTO): List<String>
}