package com.example.msp.backend.backendtry.entity

import javax.persistence.*

@Entity
data class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int,

    @Column
    var name: String,

    @Column(unique = true)
    var email: String,

    @Column
    var password: String
)