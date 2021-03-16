package com.icaro.mailsender.model

import java.math.BigDecimal

data class Message (
    val email: String,
    val personName: String,
    val account: Long,
    val value: BigDecimal,
    val amount: BigDecimal,
    val oldValue: String?
        )