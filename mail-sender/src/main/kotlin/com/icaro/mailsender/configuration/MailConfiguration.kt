package com.icaro.mailsender.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSenderImpl

import org.springframework.mail.javamail.JavaMailSender
import java.util.Properties

@Configuration
class MailConfiguration {

    @Value(value = "\${mail.host}")
    lateinit var smtpHost: String

    @Value(value = "\${mail.port}")
    lateinit var smtpPort: String

    @Value(value = "\${mail.username}")
    lateinit var smtpUsername: String

    @Value(value = "\${mail.password}")
    lateinit var smtpPassword: String

    @Value(value = "\${mail.transport.protocol}")
    lateinit var protocol: String

    @Value(value = "\${mail.smtp.auth}")
    lateinit var smtpAuth: String

    @Value(value = "\${mail.smtp.starttls.enable}")
    lateinit var smtpStarttls: String

    @Value(value = "\${mail.debug}")
    lateinit var smtpdebug: String

    @Bean
    fun getJavaMailSender(): JavaMailSender? {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = smtpHost
        mailSender.port = smtpPort.toInt()
        mailSender.username = smtpUsername
        mailSender.password = smtpPassword

        val props: Properties = mailSender.javaMailProperties
        props["mail.transport.protocol"] = protocol
        props["mail.smtp.auth"] = smtpAuth
        props["mail.smtp.starttls.enable"] = smtpStarttls
        props["mail.debug"] = smtpdebug

        return mailSender
    }
}