package com.icaro.payments.services.impl;

import com.icaro.payments.model.Payment;
import com.icaro.payments.repositories.PaymentRepository;
import com.icaro.payments.services.IAccountService;
import com.icaro.payments.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private IAccountService accountService;

    @Override
    @Transactional
    public Payment create(Payment payment) {
        payment.setAccount(accountService.findById(payment.getAccount().getId()));
        payment.getAccount().getAmount().subtract(payment.getValue());

        return repository.save(payment);
    }

    @Override
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

    @Override
    public Payment findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Payment> findAll() {
        return repository.findAll();
    }
}
