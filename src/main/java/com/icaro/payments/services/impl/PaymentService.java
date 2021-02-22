package com.icaro.payments.services.impl;

import com.icaro.payments.dto.PaymentDTO;
import com.icaro.payments.model.Payment;
import com.icaro.payments.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    private final AccountService accountService;
    
    private final ModelMapper modelMapper;
    
    private final ResourceBundle bundle;

    @Transactional
    public PaymentDTO create(PaymentDTO paymentDTO) {
    	validatePaymentDTO(paymentDTO);
    	Payment payment = convertToModel(paymentDTO);
        payment.getAccount().setAmount(payment.getAccount().getAmount().subtract(paymentDTO.getValue()));
        accountService.createOrUpdate(accountService.convertToDTO(payment.getAccount()));

        return convertToDTO(repository.save(payment));
    }

    @Transactional
    public PaymentDTO update(PaymentDTO paymentDTO) {
    	validatePaymentDTO(paymentDTO);
        Payment dbPayment = convertToModel(findById(paymentDTO.getId()));        
        reprocessPaymentValue(dbPayment, paymentDTO.getValue());        
        accountService.createOrUpdate(accountService.convertToDTO(dbPayment.getAccount()));
        
        return convertToDTO(repository.save(dbPayment));
    }

    public PaymentDTO findById(Long id) {
    	Payment payment = repository.findById(id)
    			.orElseThrow(() ->
    			new ResponseStatusException(HttpStatus.NOT_FOUND, bundle.getString("payment.notFound")));
        return convertToDTO(payment);
    }

    public List<PaymentDTO> findAll() {
        return repository.findAll()
        		.stream()
        		.map(this::convertToDTO)
        		.collect(Collectors.toList());
    }
    
    public Payment convertToModel(PaymentDTO paymentDTO) {
    	Payment payment = modelMapper.map(paymentDTO, Payment.class);
    	payment.setAccount(accountService.convertToModel(accountService.findById(paymentDTO.getAccountId())));
    	return payment;
    }
    
    public PaymentDTO convertToDTO(Payment payment) {
    	PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
    	paymentDTO.setAccountId(payment.getAccount().getId());
    	return paymentDTO;
    }
    
    private void reprocessPaymentValue(Payment payment, BigDecimal newValue) {
    	if (payment.getValue().compareTo(newValue) > 0) {
        	payment.getAccount().setAmount(payment.getAccount().getAmount().add(payment.getValue().subtract(newValue)));
        } else {
        	payment.getAccount().setAmount(payment.getAccount().getAmount().subtract(newValue.subtract(payment.getValue())));
        }
        
    	payment.setValue(newValue);
    }
    
    private void validatePaymentDTO(PaymentDTO paymentDTO) {
    	if (paymentDTO.getAccountId() == null) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("payment.accountId.required"));
    	}
    	
    	if (paymentDTO.getValue() == null) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("payment.value.required"));
    	}
    }
}
