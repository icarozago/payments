package com.icaro.payments.controllers;

import com.icaro.payments.model.Payment;
import com.icaro.payments.services.impl.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @GetMapping
    public List<Payment> findAll() {
        return service.findAll();
    }

    @PostMapping
    public Payment create(@RequestBody Payment payment) {
        return service.create(payment);
    }

    @GetMapping("/{id}")
    public Payment findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Payment update(@PathVariable Long id, @RequestBody Payment payment) {
        return service.update(payment);
    }
}
