package com.util.financialbackend.service;

import com.google.gson.Gson;
import com.util.financialbackend.model.Client;
import com.util.financialbackend.security.DTO.Login;
import com.util.financialbackend.security.DTO.Token;
import com.util.financialbackend.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ClientService service;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Gson gson;

    public Token login(Login login) {
//        var usernamePassword = new UsernamePasswordAuthenticationToken(login.username(), login.password());
//        var auth = this.authenticationManager.authenticate(usernamePassword);
        Client client = service.findByName(login.username());
        if(encoder.matches(login.password(), client.getPassword())){
            var token = tokenService.generateToken(client);
            return new Token(token);
        }
        throw new BadCredentialsException(String.format("credenciais invalidas:%s",gson.toJson(login)));
    }
}
