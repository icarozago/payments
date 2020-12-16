package com.icaro.payments.controllers;

import com.icaro.payments.model.Account;
import com.icaro.payments.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private IAccountService service;

    @PostMapping
    public Account create(@RequestBody Account account) {
        return service.create(account);
    }

    @GetMapping
    public List<Account> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Account update(@PathVariable long id, @RequestBody Account account) {
        Account dBAccount = service.findById(id);

        if (Objects.isNull(dBAccount)) {
            return null;
        }

        account.setId(dBAccount.getId());

        return service.update(account);
    }
}
