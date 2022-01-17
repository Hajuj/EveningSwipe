package com.example.user_demo_postgres.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExeceptionHandler {

    @ExceptionHandler(java.lang.Exception::class)
    fun generalExceptionHandler(exception: Exception): ResponseEntity<ApiError>{
        val error = ApiError(exception.message)
        return ResponseEntity(error , error.status)
    }

    @ExceptionHandler(MovieException::class)
    fun movieExceptionHandler(exception: Exception): ResponseEntity<ApiError>{
        val error = ApiError(exception.message)
        return ResponseEntity(error , error.status)
    }
}