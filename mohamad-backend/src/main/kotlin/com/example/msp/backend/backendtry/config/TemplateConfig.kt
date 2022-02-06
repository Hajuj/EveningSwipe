package com.example.msp.backend.backendtry.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.SimpleMailMessage

@Configuration
class TemplateConfig {
    @Bean
    fun template(): SimpleMailMessage {
        val template = SimpleMailMessage()

        template.setSubject("Verification Code")
        template.setText("""
            Hello %s,
             
             Your verification code for the app Evening-Swipe is:
             
        """.trimIndent()
        )

        return template
    }
}