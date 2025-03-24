package com.util.financialbackend.controller;

import com.google.gson.Gson;
import com.util.financialbackend.security.DTO.Login;
import com.util.financialbackend.security.DTO.Sessao;
import com.util.financialbackend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private LoginService service;
    private Gson gson;
    @PostMapping("/login")
    public ResponseEntity<Sessao> logar(@RequestBody Login login){
        log.info(String.format("Request: %s",gson.toJson(login)));
        Sessao sessao = service.generateSession(login);
        log.info(String.format("Response: %s",sessao));
        return ResponseEntity.ok(sessao);
    }
}