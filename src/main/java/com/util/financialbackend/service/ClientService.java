package com.util.financialbackend.service;

import com.util.financialbackend.model.Client;
import com.util.financialbackend.model.Spent;
import com.util.financialbackend.model.UserRole;
import com.util.financialbackend.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class ClientService {

    @Autowired
    private ClientRepository repository;
    @Autowired
    private SpentService service;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Client saveVerifying(Client c) {
        Client clientFound = repository.findClientByUsername(c.getUsername());
        if(clientFound!=null){
            throw new RuntimeException("usuario ja existente");
        }
        //nova logica para encodar senha
        clientBuilding(c);
        return repository.save(c);
    }
    public Client save(Client c) {
        clientBuilding(c);
        return repository.save(c);
    }
    public Client update(Client c){
        return repository.save(c);
    }

    private void clientBuilding(Client c){
        c.setPassword(encoder.encode(c.getPassword()));
        c.setDeleted(Boolean.FALSE);
        c.setRole(UserRole.USER);
    }
    public List<Client> list() {
        return repository.findAll();
    }

    public List<Client> listActive() {
        return repository.findAllAtiveClient();
    }

    public Client findByName(String name) {
        Client client = repository.findClientByUsername(name);
        if (client == null) {
            throw new RuntimeException("Client not found by id:" + name);
        }
        return client;
    }
    public UserDetails findByUsername(String name) {
        UserDetails client = repository.findByUsername(name);
        if (client == null) {
            throw new RuntimeException("Client not found by id:" + name);
        }
        return client;
    }
    public Client find(Long id) throws Exception {
        Optional<Client> client = repository.findById(id);
        if (client.isEmpty()) {
            throw new RuntimeException("Client not found by id:" + id);
        }
        return client.get();
    }

    public Client update(Client client, Long id) throws Exception {
        Client temp = find(id);
        temp.setName(client.getName());
        temp.setSalary(client.getSalary());
        return repository.save(temp);
    }

    public Client addSpent(String username, Spent spent) throws Exception {

        Client temp = findByName(username);
        List<Spent> spents = temp.getSpents();
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        spents.add(spent);
        spents.forEach(it -> {
            if (it.getPercentage() == null) {
                it.setPercentage((it.getPrice() * 100) / temp.getSalary());
            }
            total.set(total.get() + it.getPrice());
        });
        if (total.get() >= temp.getSalary()) {
            throw new RuntimeException("exceded salary");
        }
        service.save(spent);
        temp.setSpents(spents);
        return save(temp);
    }

    public void deleteLogic(Long id) throws Exception {
        Client client = find(id);
        client.setDeleted(true);
        repository.save(client);
    }
}
