package com.example.user_demo_postgres.entity

import javax.persistence.Entity
import javax.persistence.Id
@Entity
data class  Movie(
    @Id
    val id: Int,
    val title: String,
    val prime_title: String,
    val orig_title: String,
    val is_adult: Boolean,
    val release_year: Int,
    val end_year: Int,
    val run_time: Int,
    val genre_1: String,
    val genre_2: String,
    val genre_3: String


)