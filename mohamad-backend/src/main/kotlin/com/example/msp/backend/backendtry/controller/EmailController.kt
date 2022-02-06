package com.example.msp.backend.backendtry.controller

import com.example.msp.backend.backendtry.dto.EmailDTO
import com.example.msp.backend.backendtry.service.EmailSenderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailController(
    private val emailSenderService: EmailSenderService
) {

    @PostMapping("/api/email")
    fun sendSimpleEmail(@RequestBody request: EmailDTO): ResponseEntity<String> {
        emailSenderService.sendEmail(
            subject = request.subject,
            targetEmail = request.targetEmail,
            text = request.text
        )

        return ResponseEntity.ok("Email sent")
    }

//    @PostMapping("/api/email/template")
//    fun sendSimpleTemplateEmail(@RequestBody request: EmailRequest): ResponseEntity<Void> {
//        emailSenderService.sendEmailUsingTemplate(
//            name = request.name,
//            targetEmail = request.targetEmail
//        )
//
//        return ResponseEntity.ok("Email with template sent")
//    }
}

//class EmailRequest(
//    val subject: String?,
//    val targetEmail: String?,
//    val text: String?,
//    val name: String?
//)