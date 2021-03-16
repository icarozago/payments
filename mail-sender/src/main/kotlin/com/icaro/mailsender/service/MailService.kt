package com.icaro.mailsender.service

import com.icaro.mailsender.model.Message

class MailService {
    fun sendMail(message: Message) {
        println("Deu certo carai")
        println(message)
    }
}