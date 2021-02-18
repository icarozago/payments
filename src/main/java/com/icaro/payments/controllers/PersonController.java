package com.icaro.payments.controllers;

import com.icaro.payments.dto.PersonDTO;
import com.icaro.payments.services.impl.PersonService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @RequestMapping("/hello")
    public String helloWorld() {
        return "Hello Manolo!!";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO createPerson(@RequestBody PersonDTO personDTO) throws ResponseStatusException {
        return service.create(personDTO);
    }

    @GetMapping
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable long id) throws ResponseStatusException {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public PersonDTO update(@PathVariable long id, @RequestBody PersonDTO personDTO) {
        PersonDTO dBPerson = service.findById(id);
        personDTO.setId(dBPerson.getId());

        return service.update(personDTO);
    }
}
