package com.example.msp.backend.backendtry.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

//define the configuration bean manually
@ConstructorBinding             //bind the external properties to the constructor parameters
@ConfigurationProperties(prefix = "spring.mail")
class MailSenderProperties(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val auth: Boolean,
    val starttlsEnable: Boolean,
)

@Configuration
@EnableConfigurationProperties(MailSenderProperties::class)
class MailSenderConfig(
    private val mailSenderProperties: MailSenderProperties
) {
    //define JavaMailSender bean
    @Bean
    fun javaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = mailSenderProperties.host
        mailSender.port = mailSenderProperties.port
        mailSender.username = mailSenderProperties.username
        mailSender.password = mailSenderProperties.password

        configureJavaMailProperties(mailSender.javaMailProperties)
        return mailSender
    }

    private fun configureJavaMailProperties(properties: Properties) {
        properties["mail.smtp.auth"] = mailSenderProperties.auth
        properties["mail.smtp.starttls.enable"] = mailSenderProperties.starttlsEnable
    }
}