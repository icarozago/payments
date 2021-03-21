package com.icaro.mailsender.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.icaro.mailsender.model.Message
import com.icaro.mailsender.service.MailService
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.Properties

@Component
class MessageConsumer (private val mailService: MailService) {

    @KafkaListener(topics = ["payments"], groupId = "payments")
    fun consume(message: String) {
        System.err.println(message)
        val jsonMapper = ObjectMapper().apply { registerKotlinModule() }
        mailService.sendMail(jsonMapper.readValue(message, Message::class.java))
    }
}
