package com.icaro.payments.controllers;

import com.icaro.payments.dto.AccountDTO;
import com.icaro.payments.model.Account;
import com.icaro.payments.services.impl.AccountService;
import com.icaro.payments.services.impl.PersonService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;
    
    private final PersonService personService;
    
    private final ModelMapper modelMapper;

    @PostMapping
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        return convertToDTO(service.create(convertToModel(accountDTO)));
    }

    @GetMapping
    public List<AccountDTO> findAll() {
        return service.findAll()
        		.stream()
        		.map(this::convertToDTO)
        		.collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDTO findById(@PathVariable long id) {
        return convertToDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public AccountDTO update(@PathVariable long id, @RequestBody AccountDTO accountDTO) {
        Account dBAccount = service.findById(id);

        if (Objects.isNull(dBAccount)) {
            return null;
        }

        accountDTO.setId(dBAccount.getId());

        return convertToDTO(service.update(convertToModel(accountDTO)));
    }
    
    private Account convertToModel(AccountDTO accountDTO) {
    	Account account = modelMapper.map(accountDTO, Account.class);
    	
    	if (accountDTO.getPersonId() != null) {
    		account.setPerson(personService.findById(accountDTO.getPersonId()));
    	}
    	
    	return account;
    }
    
    private AccountDTO convertToDTO(Account account) {
    	AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
    	accountDTO.setPersonId(account.getPerson().getId());
    	return accountDTO;
    }
}
