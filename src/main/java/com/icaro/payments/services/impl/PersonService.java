package com.icaro.payments.services.impl;

import com.icaro.payments.dto.PersonDTO;
import com.icaro.payments.model.Person;
import com.icaro.payments.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;
    
    private final ModelMapper modelMapper;

    public List<PersonDTO> findAll() {
        return repository.findAll()
        		.stream()
        		.map(this::convertToDTO)
        		.collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) {
    	Person person = repository.findById(id).orElse(null);
        return person != null ? convertToDTO(person) : null;
    }

    public PersonDTO create(PersonDTO personDTO) {
        return convertToDTO(repository.save(convertToModel(personDTO)));
    }

    public PersonDTO update(PersonDTO personDTO) {
        return convertToDTO(repository.save(convertToModel(personDTO)));
    }
    
    public Person convertToModel(PersonDTO personDTO) {
    	return modelMapper.map(personDTO, Person.class);
    }
    
    public PersonDTO convertToDTO(Person person) {
    	return modelMapper.map(person, PersonDTO.class);
    }
}
