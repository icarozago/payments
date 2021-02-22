package com.icaro.payments.controllers;

import com.icaro.payments.dto.AccountDTO;
import com.icaro.payments.services.impl.AccountService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        return service.createOrUpdate(accountDTO);
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
        accountDTO.setId(id);
        return service.createOrUpdate(accountDTO);
    }
}
