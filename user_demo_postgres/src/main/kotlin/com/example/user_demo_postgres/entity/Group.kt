package com.example.user_demo_postgres.entity

import net.bytebuddy.asm.Advice
import javax.persistence.*

@Entity
@Table(name = "gruppe")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    var id: Int,
    @Column(name="name")
    var name: String,
    @Column(name="description")
    var description: String,

    var owner: Int
)
