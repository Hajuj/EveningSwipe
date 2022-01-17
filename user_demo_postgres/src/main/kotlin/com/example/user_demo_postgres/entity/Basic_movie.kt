package com.example.user_demo_postgres.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
data class Basic_movie(

    @Id
    val id: String,
    val title: String,
    val prime_title: String,
    val orig_title: String,
    val is_adult: Boolean,
    val rel_date: Int,
    val end_date: Int,
    val runtime: Int,
    val genre_1: String,
    val genre_2: String,
    val genre_3: String,
    val rating: Double,
    val votes: Int
)
