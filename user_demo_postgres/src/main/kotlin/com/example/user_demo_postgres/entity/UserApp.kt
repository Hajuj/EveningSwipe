package com.example.user_demo_postgres.entity

import javax.persistence.*

@Entity
data class UserApp(


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int,

    @Column
    var name: String,

    @Column(unique = true)
    var email: String ,

    @Column
    var password: String,

    )