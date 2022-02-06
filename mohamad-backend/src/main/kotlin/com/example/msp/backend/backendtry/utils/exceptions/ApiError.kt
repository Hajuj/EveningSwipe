package com.example.msp.backend.backendtry.utils.exceptions

import org.springframework.http.HttpStatus

data class ApiError(

    val message: String,
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
    val code: Int = status.value()

)