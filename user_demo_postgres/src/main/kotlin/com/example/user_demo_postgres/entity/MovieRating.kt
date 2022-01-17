package com.example.user_demo_postgres.entity

import javax.persistence.*

@Entity
//@Table(name = "movieRating")
data class MovieRating(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,
    val movie_id: String,
    val filter_id: Int,
    val rating: Int
)
