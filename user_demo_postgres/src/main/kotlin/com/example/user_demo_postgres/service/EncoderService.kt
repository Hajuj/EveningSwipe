package com.example.user_demo_postgres.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class EncoderService {

     val encoder = BCryptPasswordEncoder()
}