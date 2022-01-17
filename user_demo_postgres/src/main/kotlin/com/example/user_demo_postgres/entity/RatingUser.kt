package com.example.user_demo_postgres.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class RatingUser(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int,
    var filterId: Int,
    var userId: Int,
    var rating: Int

)
