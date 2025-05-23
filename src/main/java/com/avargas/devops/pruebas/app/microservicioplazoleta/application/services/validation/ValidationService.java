package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationService {

    public ResponseEntity<?> validate(BindingResult result){
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }
}
