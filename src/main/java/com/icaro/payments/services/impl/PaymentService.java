package com.icaro.payments.services.impl;

import com.icaro.payments.dto.PaymentDTO;
import com.icaro.payments.model.Payment;
import com.icaro.payments.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    private final AccountService accountService;
    
    private final ModelMapper modelMapper;

    @Transactional
    public PaymentDTO create(PaymentDTO paymentDTO) {
    	Payment payment = convertToModel(paymentDTO);
        payment.getAccount().setAmount(payment.getAccount().getAmount().subtract(paymentDTO.getValue()));

        return convertToDTO(repository.save(payment));
    }

    @Transactional
    public PaymentDTO update(PaymentDTO paymentDTO) {

        Optional<Payment> dbPayment = repository.findById(paymentDTO.getId());

        if (!dbPayment.isPresent()) {
            return null;
        }

        if (dbPayment.get().getValue().compareTo(paymentDTO.getValue()) > 0) {
        	dbPayment.get().getAccount().setAmount(dbPayment.get().getAccount().getAmount().add(dbPayment.get().getValue().subtract(paymentDTO.getValue())));
        } else {
        	dbPayment.get().getAccount().setAmount(dbPayment.get().getAccount().getAmount().subtract(paymentDTO.getValue().subtract(dbPayment.get().getValue())));
        }

        return convertToDTO(repository.save(dbPayment.get()));
    }

    public PaymentDTO findById(Long id) {
    	Payment payment = repository.findById(id).orElse(null);
        return payment != null ? convertToDTO(payment) : null;
    }

    public List<PaymentDTO> findAll() {
        return repository.findAll()
        		.stream()
        		.map(this::convertToDTO)
        		.collect(Collectors.toList());
    }
    
    private Payment convertToModel(PaymentDTO paymentDTO) {
    	Payment payment = modelMapper.map(paymentDTO, Payment.class);
    	payment.setAccount(accountService.findEntityById(paymentDTO.getAccountId()));
    	return payment;
    }
    
    private PaymentDTO convertToDTO(Payment payment) {
    	PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
    	paymentDTO.setAccountId(payment.getAccount().getId());
    	return paymentDTO;
    }
}
