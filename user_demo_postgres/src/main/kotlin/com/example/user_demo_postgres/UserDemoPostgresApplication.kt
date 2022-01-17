package com.example.user_demo_postgres

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class UserDemoPostgresApplication

fun main(args: Array<String>) {
    runApplication<UserDemoPostgresApplication>(*args)
}
