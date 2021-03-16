package com.icaro.payments.controllers;

import com.icaro.payments.dto.PaymentDTO;
import com.icaro.payments.services.impl.PaymentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @GetMapping
    public List<PaymentDTO> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDTO create(@RequestBody PaymentDTO paymentDTO) {
        return service.create(paymentDTO);
    }

    @GetMapping("/{id}")
    public PaymentDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public PaymentDTO update(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
    	paymentDTO.setId(id);
        return service.update(paymentDTO);
    }
}
