package com.wabs.website.repository;

import com.wabs.website.models.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmailRepository extends CrudRepository<Email, Long> {

    Email searchByEmail(String email);

    List<Email> findAll();

    @Query("SELECT e FROM Email e WHERE e.updates = TRUE")
    List<Email> findAllWithUpdates();
}
