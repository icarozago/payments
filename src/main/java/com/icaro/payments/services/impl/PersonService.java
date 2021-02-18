package com.icaro.payments.services.impl;

import com.icaro.payments.dto.PersonDTO;
import com.icaro.payments.model.Person;
import com.icaro.payments.repositories.PersonRepository;

import io.micrometer.core.instrument.util.StringUtils;
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
public class PersonService {

    private final PersonRepository repository;
    
    private final ModelMapper modelMapper;
    
    private final ResourceBundle bundle = ResourceBundle.getBundle("resource");

    public List<PersonDTO> findAll() {
        return repository.findAll()
        		.stream()
        		.map(this::convertToDTO)
        		.collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws ResponseStatusException {
    	Person person = repository.findById(id)
    			.orElseThrow(() -> 
    			new ResponseStatusException(HttpStatus.NOT_FOUND, bundle.getString("person.notFound")));
        return convertToDTO(person);
    }

    public PersonDTO create(PersonDTO personDTO) throws ResponseStatusException {
    	validatePerson(personDTO);
        return convertToDTO(repository.save(convertToModel(personDTO)));
    }

    public PersonDTO update(PersonDTO personDTO) {
    	validatePerson(personDTO);
        return convertToDTO(repository.save(convertToModel(personDTO)));
    }
    
    public Person convertToModel(PersonDTO personDTO) {
    	return modelMapper.map(personDTO, Person.class);
    }
    
    public PersonDTO convertToDTO(Person person) {
    	return modelMapper.map(person, PersonDTO.class);
    }
    

	private void validatePerson(PersonDTO personDTO) {
		if (StringUtils.isBlank(personDTO.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("person.name.required"));
    	}
    	
    	if (StringUtils.isBlank(personDTO.getCpf())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("person.cpf.required"));
    	}
    	
    	if (StringUtils.isBlank(personDTO.getEmail())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bundle.getString("person.email.required"));
    	}
	}
}
