package com.util.financialbackend.service;

import com.util.financialbackend.model.Client;
import com.util.financialbackend.model.Spent;
import com.util.financialbackend.repository.SpentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SpentService {

    @Autowired
    private SpentRepository repository;

    public Spent save(Spent s){
        return repository.save(s);
    }
    public List<Spent> list(){
        return repository.findAll();
    }
    public Spent find(Long id) throws Exception {
        var temp = repository.findById(id);
        if(temp.isEmpty()){
            throw new Exception("Spent not found by id:"+id);
        }
        return temp.get();
    }
    public Spent update(Spent spent, Long id) throws Exception {
        Spent temp = find(id);
        temp.setName(spent.getName());
        temp.setPrice(spent.getPrice());
        temp.setPercentage(spent.getPercentage());
        List<Spent> spents = repository.findAll();
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        spents.add(spent);
        spents.forEach(it->{
            if (it.getPercentage()==null){
                temp.setPercentage((it.getPrice()*100)/5500.0);
            }
            total.set(total.get() + it.getPrice());
        });
        if(total.get() >= 5500.0){
            throw new Exception("exceded salary");
        }
        return repository.save(temp);
    }
    public void delete(Long id) throws Exception {
        find(id);
        repository.deleteById(id);
    }
}
