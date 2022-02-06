package com.example.msp.backend.backendtry.dto

data class EmailDTO(
    val subject: String,
    val targetEmail: String,
    val text: String,
    val name: String
)