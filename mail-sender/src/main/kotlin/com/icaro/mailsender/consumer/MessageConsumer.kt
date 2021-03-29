package com.icaro.mailsender.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.icaro.mailsender.model.Message
import com.icaro.mailsender.service.MailService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MessageConsumer (private val mailService: MailService) {

    @KafkaListener(topics = ["payments"],
        groupId = "payments",
        containerFactory = "filterKafkaListenerContainerFactory")
    fun consume(message: String) {
        System.err.println(message)
        val jsonMapper = ObjectMapper().apply { registerKotlinModule() }
        mailService.sendMail(jsonMapper.readValue(message, Message::class.java))
    }
}
