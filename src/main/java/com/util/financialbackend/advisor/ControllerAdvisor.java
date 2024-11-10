package com.util.financialbackend.advisor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Error> advise(Exception e){
        return ResponseEntity.status(400).body(new Error(e.getMessage()));
    }
}
