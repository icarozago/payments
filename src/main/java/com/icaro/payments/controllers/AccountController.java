package com.icaro.payments.controllers;

import com.icaro.payments.model.Account;
import com.icaro.payments.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountRepository repository;

    @PostMapping
    public Account create(@RequestBody Account account) {
        repository.save(account);

        return account;
    }

    @GetMapping
    public List<Account> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Account update(@PathVariable long id, @RequestBody Account account) {
        Optional<Account> dBAccount = repository.findById(id);

        if (!dBAccount.isPresent()) {
            return null;
        }

        account.setId(dBAccount.get().getId());

        repository.save(account);

        return account;
    }
}
