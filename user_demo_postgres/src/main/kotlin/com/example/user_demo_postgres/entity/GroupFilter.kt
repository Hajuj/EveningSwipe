package com.example.user_demo_postgres.entity

import javax.persistence.*

@Entity
@Table(name = "filter")
data class GroupFilter(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int ,
    var genre_1: String,
    val genre_2: String,
    val genre_3: String,
    val min_year: Int,
    val max_year: Int,
    val rating: Double,
    val votes: Int,
    val max_runtime: Int,
    val size: Int,
    val group_id: Int,
    val selection: String

)
