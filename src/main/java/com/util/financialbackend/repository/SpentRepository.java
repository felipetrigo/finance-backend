package com.util.financialbackend.repository;

import com.util.financialbackend.model.Spent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpentRepository extends JpaRepository<Spent,Long> {

}
