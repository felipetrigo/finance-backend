package com.util.financialbackend.controller;

import com.google.gson.Gson;
import com.util.financialbackend.security.DTO.Login;
import com.util.financialbackend.security.DTO.Token;
import com.util.financialbackend.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RestController
@RequestMapping("/v1/users")
public class LoginController {
    @Autowired
    private LoginService service;
    @Autowired
    private Gson gson;

    @PostMapping("/login")
    public ResponseEntity<Token> logar(@RequestBody Login login) {
        log.info(String.format("request:%s", gson.toJson(login)));
        return ResponseEntity.ok(service.login(login));
    }
}