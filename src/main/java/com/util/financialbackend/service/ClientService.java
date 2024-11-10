package com.util.financialbackend.service;

import com.util.financialbackend.model.Client;
import com.util.financialbackend.model.Spent;
import com.util.financialbackend.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Client save(Client c){
        return repository.save(c);
    }
    public List<Client> list(){
        return repository.findAll();
    }
    public List<Client> listActive(){
        return repository.findAllAtiveClient();
    }
    public Client find(Long id) throws Exception {
        Optional<Client> client = repository.findById(id);
        if(client.isEmpty()){
            throw new Exception("Client not found by id"+id);
        }
        return client.get();
    }
    public Client update(Client client,Long id) throws Exception {
        Client temp = find(id);
        temp.setName(client.getName());
        temp.setSalary(client.getSalary());
        return repository.save(temp);
    }
    public Client addSpent(Long idClient, Spent spent) throws Exception {

        Client temp = find(idClient);
        List<Spent> spents = temp.getSpents();
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        spents.add(spent);
        spents.forEach(it->{
            if (it.getPercentage()==null){
                it.setPercentage((it.getPrice()*100)/temp.getSalary());
            }
            total.set(total.get() + it.getPrice());
        });
        if(total.get() >= temp.getSalary()){
            throw new Exception("exceded salary");
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
