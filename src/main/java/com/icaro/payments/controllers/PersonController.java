package com.icaro.payments.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people")
public class PersonController {

    @RequestMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<String>("Hello Manolo!!", HttpStatus.OK);
    }


}
