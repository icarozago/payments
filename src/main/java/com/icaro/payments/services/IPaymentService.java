package com.icaro.payments.services;

import com.icaro.payments.model.Payment;

import java.util.List;

public interface IPaymentService {

    public Payment create(Payment payment);

    public Payment update(Payment payment);

    public Payment findById(Long id);

    public List<Payment> findAll();
}
