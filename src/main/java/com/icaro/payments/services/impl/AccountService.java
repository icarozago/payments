package com.icaro.payments.services.impl;

import com.icaro.payments.model.Account;
import com.icaro.payments.model.Person;
import com.icaro.payments.repositories.AccountRepository;
import com.icaro.payments.services.IAccountService;
import com.icaro.payments.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private IPersonService personService;

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Account create(Account account) {
        account.setPerson(personService.findById(account.getPerson().getId()));

        return repository.save(account);
    }

    @Override
    public Account update(Account account) {
        return repository.save(account);
    }
}
