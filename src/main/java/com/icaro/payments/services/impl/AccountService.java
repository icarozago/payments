package com.icaro.payments.services.impl;

import com.icaro.payments.model.Account;
import com.icaro.payments.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PersonService personService;

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Account create(Account account) {
        account.setPerson(personService.findById(account.getPerson().getId()));

        return repository.save(account);
    }

    public Account update(Account account) {
        return repository.save(account);
    }
}
