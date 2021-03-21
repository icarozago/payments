package com.icaro.mailsender.service

import com.icaro.mailsender.model.Message
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService (private val mailSender: JavaMailSender) {

    val defaultFrom = "noreply@icarospayments.com"

    fun sendMail(message: Message) {
        val mail = SimpleMailMessage()
        mail.setFrom(defaultFrom)
        mail.setTo(message.email)
        mail.setSubject("Payment received!")
        mail.setText(buildMailText(message))

        mailSender.send(mail)
    }

    fun buildMailText(message: Message): String {
        val builder = StringBuilder("Dear ")
            .append(message.personName)
            .append(", \n\n")
            .append("We received a")
            .append(if (message.oldValue != null) "n update of a " else " new ")
            .append("payment with value of ")
            .append(message.value)
            .append("!\nYour account ")
            .append(message.account)
            .append(" has now the amount of ")
            .append(message.amount)
            .append(".\n")

        if (message.oldValue != null) {
            builder.append("The old value of this payment was ")
                .append(message.oldValue)
                .append(".")
        }

        return builder.toString()
    }
}