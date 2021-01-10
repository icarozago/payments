package com.icaro.payments.controllers;

import com.icaro.payments.dto.PaymentDTO;
import com.icaro.payments.model.Payment;
import com.icaro.payments.services.impl.AccountService;
import com.icaro.payments.services.impl.PaymentService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;
    
    private final AccountService accountService;
    
    private final ModelMapper modelMapper;

    @GetMapping
    public List<PaymentDTO> findAll() {
        return service.findAll()
        		.stream()
        		.map(this::convertToDTO)
        		.collect(Collectors.toList());
    }

    @PostMapping
    public PaymentDTO create(@RequestBody PaymentDTO paymentDTO) {
        return convertToDTO(service.create(convertToModel(paymentDTO)));
    }

    @GetMapping("/{id}")
    public PaymentDTO findById(@PathVariable Long id) {
        return convertToDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public PaymentDTO update(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        return convertToDTO(service.update(convertToModel(paymentDTO)));
    }
    
    private Payment convertToModel(PaymentDTO paymentDTO) {
    	Payment payment = modelMapper.map(paymentDTO, Payment.class);
    	payment.setAccount(accountService.findById(paymentDTO.getAccountId()));
    	return payment;
    }
    
    private PaymentDTO convertToDTO(Payment payment) {
    	PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
    	paymentDTO.setAccountId(payment.getAccount().getId());
    	return paymentDTO;
    }
}
