package com.icaro.payments.services.impl;

import com.icaro.payments.dto.AccountDTO;
import com.icaro.payments.model.Account;
import com.icaro.payments.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    private final PersonService personService;
    
    private final ModelMapper modelMapper;

    public List<AccountDTO> findAll() {
        return repository.findAll()
        		.stream()
        		.map(this::convertToDTO)
        		.collect(Collectors.toList());
    }

    public AccountDTO findById(Long id) {
        Account account = findEntityById(id);
		return account != null ? convertToDTO(account) : null;
    }

    public AccountDTO create(AccountDTO accountDTO) {
    	Account account = convertToModel(accountDTO);
        account.setPerson(personService.findEntityById(accountDTO.getPersonId()));

        return convertToDTO(repository.save(account));
    }

    public AccountDTO update(AccountDTO accountDTO) {
        return convertToDTO(repository.save(convertToModel(accountDTO)));
    }
    
    public Account findEntityById(Long id) {
    	return repository.findById(id).orElse(null);
    }
    
    private Account convertToModel(AccountDTO accountDTO) {
    	Account account = modelMapper.map(accountDTO, Account.class);
    	
    	if (accountDTO.getPersonId() != null) {
    		account.setPerson(personService.findEntityById(accountDTO.getPersonId()));
    	}
    	
    	return account;
    }
    
    private AccountDTO convertToDTO(Account account) {
    	AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
    	accountDTO.setPersonId(account.getPerson().getId());
    	return accountDTO;
    }
}
