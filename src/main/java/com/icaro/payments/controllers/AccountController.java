package com.icaro.payments.controllers;

import com.icaro.payments.dto.AccountDTO;
import com.icaro.payments.services.impl.AccountService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        return service.create(accountDTO);
    }

    @GetMapping
    public List<AccountDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AccountDTO findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public AccountDTO update(@PathVariable long id, @RequestBody AccountDTO accountDTO) {
        AccountDTO dBAccount = service.findById(id);

        if (Objects.isNull(dBAccount)) {
            return null;
        }

        accountDTO.setId(dBAccount.getId());

        return service.update(accountDTO);
    }
}
