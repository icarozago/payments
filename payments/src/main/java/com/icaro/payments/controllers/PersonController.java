package com.icaro.payments.controllers;

import com.icaro.payments.dto.PersonDTO;
import com.icaro.payments.services.impl.PersonService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
        return service.createOrUpdate(personDTO);
    }

    @GetMapping
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public PersonDTO update(@PathVariable long id, @RequestBody PersonDTO personDTO) {
        personDTO.setId(id);
        return service.createOrUpdate(personDTO);
    }
}
