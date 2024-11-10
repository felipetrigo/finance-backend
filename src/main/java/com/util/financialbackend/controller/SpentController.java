package com.util.financialbackend.controller;

import com.util.financialbackend.DTO.ClientSpentsResponseDTO;
import com.util.financialbackend.DTO.SpentRequestDTO;
import com.util.financialbackend.model.Client;
import com.util.financialbackend.model.Spent;
import com.util.financialbackend.service.ClientService;
import com.util.financialbackend.service.SpentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
@RestController
@RequestMapping("/v1/spent")
public class SpentController {
    @Autowired
    private SpentService service;
    @Autowired
    private ClientService clientService;

    @GetMapping("/list")
    public ResponseEntity<List<Spent>> getSpents() {
        log.info("Request list spents");
        var list = service.list();
        log.info(String.format("Response list spents: %S",list));
        return ResponseEntity.ok(list);
    }

    @GetMapping("/get")
    public ResponseEntity<Spent> getSpent(@Param("id") Long id) throws Exception {
        return ResponseEntity.ok(service.find(id));
    }
    @GetMapping("/query")
    public ResponseEntity<ClientSpentsResponseDTO> getSpentsByClientId(@Param("id") Long id) throws Exception {
        log.info(String.format("Request query spents by client id: %d",id));
        Client c = clientService.find(id);
        log.info(String.format("Response query spents by client id: %d - response: %S",id,c));
        return ResponseEntity.ok(new ClientSpentsResponseDTO(c.getId(),c.getName(),c.getSalary(),c.getSpents()));
    }
    @PutMapping("/update")
    public ResponseEntity<Spent> updateSpents(@RequestBody Spent update) throws Exception {
        log.info(String.format("Request Update spent: %S",update));
        var response = service.update(update, update.getId());
        log.info(String.format("Response Update spent: %S",response));
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete")
    public ResponseEntity daleteSpents(@Param("id") Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
