package com.icaro.payments.services.impl;

import com.icaro.payments.dto.AccountDTO;
import com.icaro.payments.model.Account;
import com.icaro.payments.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    private final PersonService personService;
    
    private final ModelMapper modelMapper;
    
    private final ResourceBundle bundle;

    public List<AccountDTO> findAll() {
        return repository.findAll()
        		.stream()
        		.map(this::convertToDTO)
        		.collect(Collectors.toList());
    }

    public AccountDTO findById(Long id) {
        Account account = repository.findById(id)
        		.orElseThrow(() -> 
    			new ResponseStatusException(HttpStatus.NOT_FOUND, bundle.getString("account.notFound")));
		return convertToDTO(account);
    }

    public AccountDTO createOrUpdate(AccountDTO accountDTO) {
    	validateAccountDTO(accountDTO);
    	
    	Account account = convertToModel(accountDTO);
        account.setPerson(personService.convertToModel(personService.findById(accountDTO.getPersonId())));

        return convertToDTO(repository.save(account));
    }
    
    public Account convertToModel(AccountDTO accountDTO) {
    	Account account = modelMapper.map(accountDTO, Account.class);
    	
    	if (accountDTO.getPersonId() != null) {
    		account.setPerson(personService.convertToModel(personService.findById(accountDTO.getPersonId())));
    	}
    	
    	return account;
    }
    
    public AccountDTO convertToDTO(Account account) {
    	AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
    	accountDTO.setPersonId(account.getPerson().getId());
    	return accountDTO;
    }
    
    private void validateAccountDTO(AccountDTO accountDTO) {
    	if (accountDTO.getAmount() == null) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("account.amount.required"));
    	}
    	
    	if (accountDTO.getNumber() == null || accountDTO.getNumber() <= 0) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("account.number.invalid"));
    	}
    	
    	if (accountDTO.getPersonId() == null) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("account.personId.required"));
    	}
    }
}
