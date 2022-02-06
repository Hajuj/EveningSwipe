//package com.example.msp.backend.backendtry.service
//
//import Properties.MailSenderProperties
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.context.properties.EnableConfigurationProperties
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.mail.SimpleMailMessage
//import org.springframework.mail.javamail.JavaMailSender
//import org.springframework.mail.javamail.JavaMailSenderImpl
//import org.springframework.stereotype.Component
//
//@Configuration
//@EnableConfigurationProperties(MailSenderProperties::class)
//class EmailServiceImpl(
//
//    private val emailSender: MailSenderProperties
//)
//
//@Bean
//fun javaMailSender(): JavaMailSender {
//    val mailSender = JavaMailSenderImpl()
//    mailSender.host = emailSender.host
//    mailSender.port = mailSenderProperties.port
//    mailSender.username = mailSenderProperties.username
//    mailSender.password = mailSenderProperties.password
//    configureJavaMailProperties(mailSender.javaMailProperties)
//    return mailSender
//}
//    fun sendSimpleMessage(
//        to: String?, subject: String?, text: String?
//    ) {
//        val message = SimpleMailMessage()
//        message.setFrom("noreply@evening-swipe.com")
//        message.setTo(to)
//        message.setSubject(subject)
//        message.setText(text)
//        emailSender.send(message)
//    }
//)