package com.icaro.payments.controllers;

import com.icaro.payments.dto.PersonDTO;
import com.icaro.payments.model.Person;
import com.icaro.payments.services.impl.PersonService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;
    
    private final ModelMapper modelMapper;

    @RequestMapping("/hello")
    public String helloWorld() {
        return "Hello Manolo!!";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
        return convertToDTO(service.create(convertToModel(personDTO)));
    }

    @GetMapping
    public List<PersonDTO> findAll() {
        return service.findAll()
        		.stream()
        		.map(this::convertToDTO)
        		.collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable long id) {
        return convertToDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public PersonDTO update(@PathVariable long id, @RequestBody PersonDTO personDTO) {
        Person dBPerson = service.findById(id);

        if (Objects.isNull(dBPerson)) {
            return null;
        }

        personDTO.setId(dBPerson.getId());

        return convertToDTO(service.update(convertToModel(personDTO)));
    }
    
    private Person convertToModel(PersonDTO personDTO) {
    	return modelMapper.map(personDTO, Person.class);
    }
    
    private PersonDTO convertToDTO(Person person) {
    	return modelMapper.map(person, PersonDTO.class);
    }

//    @GetMapping
//    public List<Person> findByName(@RequestHeader String name) {
//        return repository.findByName(name);
//    }
}
