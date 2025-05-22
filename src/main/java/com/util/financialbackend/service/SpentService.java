package com.util.financialbackend.service;

import com.util.financialbackend.model.Client;
import com.util.financialbackend.model.Spent;
import com.util.financialbackend.repository.ClientRepository;
import com.util.financialbackend.repository.SpentRepository;
import com.util.financialbackend.security.DTO.SpentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SpentService {

    @Autowired
    private SpentRepository repository;
    @Autowired
    private ClientRepository clientRepository;
    public Spent save(Spent s){
        return repository.save(s);
    }
    public List<Spent> list(){
        return repository.findAll();
    }
    public Spent find(Long id) throws Exception {
        var temp = repository.findById(id);
        if(temp.isEmpty()){
            throw new RuntimeException("Spent not found by id:"+id);
        }
        return temp.get();
    }
    public Spent update(SpentDTO spent, Long id) throws Exception {
        var user = clientRepository.findClientByUsername(spent.getUsername());
        Spent temp = find(id);
        temp.setName(spent.getName());
        temp.setPrice(spent.getPrice());
        List<Spent> spents = repository.findAll();
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        spents.add(temp);
        spents.forEach(it->{
            if (it.getPercentage()==null){
                temp.setPercentage((it.getPrice()*100)/user.getSalary());
            }
            total.set(total.get() + it.getPrice());
        });
        if(total.get() >= user.getSalary()){
            throw new RuntimeException("exceded salary");
        }
        return repository.save(temp);
    }
    public void delete(Long id) throws Exception {
        find(id);
        repository.deleteById(id);
    }
}
