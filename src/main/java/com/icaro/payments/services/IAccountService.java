package com.icaro.payments.services;

import com.icaro.payments.model.Account;

import java.util.List;

public interface IAccountService {

    public List<Account> findAll();

    public Account findById(Long id);

    public Account create(Account account);

    public Account update(Account account);
}
