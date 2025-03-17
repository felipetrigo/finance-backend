package com.util.financialbackend.controller;

import com.util.financialbackend.DTO.ClientRequestDTO;
import com.util.financialbackend.DTO.ClientResponseDTO;
import com.util.financialbackend.DTO.SpentRequestDTO;
import com.util.financialbackend.model.Client;
import com.util.financialbackend.model.Spent;
import com.util.financialbackend.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/list")
    public ResponseEntity<List<ClientResponseDTO>> getClients() {
        List<Client> list = clientService.listActive();
        log.info(String.format("Response clientes ativos - %S",list));
        return ResponseEntity.ok(list
                .stream()
                .map(it -> new ClientResponseDTO(it.getName(), it.getSalary(),it.getPhoneNumber(), it.getEmail()))
                .collect(Collectors.toList()));
    }
    @GetMapping("/get")
    public ResponseEntity<ClientResponseDTO> getClient(String username) throws Exception {
        log.info(String.format("Request cliente ativos by id - %s",username));
        var original = clientService.findByName(username);
        log.info(String.format("Response cliente ativos by id: %s - response: %S",username,original));
        return ResponseEntity.ok(new ClientResponseDTO(original.getName(), original.getSalary(),original.getPhoneNumber(),original.getEmail()));
    }

    @PostMapping("/save")
    public ResponseEntity<ClientResponseDTO> saveClient(@RequestBody ClientRequestDTO c) {
        log.info(String.format("Request salvar cliente %S",c));
        Client original = clientService.save(Client.builder()
                        .name(c.getName())
                        .username(c.getUsername())
                        .password(c.getPassword())
                        .phoneNumber(c.getPhoneNumber())
                        .salary(c.getSalary())
                        .email(c.getEmail())
                .build());
        log.info(String.format("Response salvar cliente %S",original));
        return ResponseEntity
                .status(201)
                .body(new ClientResponseDTO(original.getName(), original.getSalary(),original.getPhoneNumber(),original.getEmail()));
    }

    @PutMapping("/add/spent")
    public ResponseEntity<ClientResponseDTO> addSpentToClient(@Param("clientId") Long id, @RequestBody SpentRequestDTO spent) throws Exception {
        log.info(String.format("Request salvar gastos id de cliente: %d - spent: %S",id,spent));
        Client original = clientService.addSpent(id, new Spent(
                null,
                spent.getPrice(),
                null,
                spent.getName()));
        log.info(String.format("Response salvar gastos id de cliente: %d - response: %S",id,original));
        return ResponseEntity.ok(new ClientResponseDTO(original.getName(), original.getSalary(),original.getPhoneNumber(),original.getEmail()));
    }
}
