package com.icaro.payments.services.impl;

import com.icaro.payments.model.Payment;
import com.icaro.payments.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    private final AccountService accountService;

    @Transactional
    public Payment create(Payment payment) {
        payment.setAccount(accountService.findById(payment.getAccount().getId()));
        payment.getAccount().getAmount().subtract(payment.getValue());

        return repository.save(payment);
    }

    @Transactional
    public Payment update(Payment payment) {

        Optional<Payment> dbPayment = repository.findById(payment.getId());

        if (!dbPayment.isPresent()) {
            return null;
        }

        if (dbPayment.get().getValue().compareTo(payment.getValue()) > 0) {
            payment.getAccount().getAmount().add(dbPayment.get().getValue().subtract(payment.getValue()));
        } else {
            payment.getAccount().getAmount().subtract(payment.getValue().subtract(dbPayment.get().getValue()));
        }

        return repository.save(payment);
    }

    public Payment findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Payment> findAll() {
        return repository.findAll();
    }
}
