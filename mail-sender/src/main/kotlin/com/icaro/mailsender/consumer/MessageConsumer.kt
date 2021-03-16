package com.icaro.mailsender.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.icaro.mailsender.model.Message
import com.icaro.mailsender.service.MailService
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.Properties

class MessageConsumer

private fun createConsumer(brokers: String): Consumer<String, String> {
    val props = Properties()
    props["bootstrap.servers"] = brokers
    props["group.id"] = "payment-processor"
    props["key.deserializer"] = StringDeserializer::class.java
    props["value.deserializer"] = StringDeserializer::class.java
    return KafkaConsumer<String, String>(props)
}

fun main(args: Array<String>) {
    val jsonMapper = ObjectMapper().apply {registerKotlinModule()}
    val consumer = createConsumer("localhost:9092")
    val mailService = MailService()
    consumer.subscribe(listOf("payments"))
    while (true) {
        val records = consumer.poll(Duration.ofSeconds(1))
        records.iterator().forEach {
            System.err.println(it.value())
            println(it.value())
            mailService.sendMail(jsonMapper.readValue(it.value(), Message::class.java))
        }
    }
}
