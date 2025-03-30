package com.util.financialbackend.repository;

import com.util.financialbackend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    @Query("select c from Client c where c.id = :id and c.deleted = false")
    Client findAtiveClient(@Param("id") Long id);
    @Query("select c from Client c where c.deleted = false")
    List<Client> findAllAtiveClient();
    @Query("select c from Client c where c.id = :id and c.deleted = true")
    Client findDeletedClient(@Param("id") Long id);
    @Query("select c from Client c where c.deleted = true")
    List<Client> findAllDeletedClient();

    @Query("SELECT c FROM Client c WHERE c.username= (:username)")
    Client findClientByUsername(@Param("username") String username);

    UserDetails findByUsername(@Param("username") String username);
}