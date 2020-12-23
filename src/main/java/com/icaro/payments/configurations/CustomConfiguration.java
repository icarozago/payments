package com.icaro.payments.configurations;

import com.icaro.payments.services.impl.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService();
    }
}
