package com.util.financialbackend.service;

import com.util.financialbackend.model.Client;
import com.util.financialbackend.security.DTO.Login;
import com.util.financialbackend.security.DTO.Token;
import com.util.financialbackend.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TokenService tokenService;

    public Token login(Login login) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.username(), login.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Client) auth.getPrincipal());
        return new Token(token);
    }
}
