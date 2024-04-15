package com.example.java_backend.backend;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.java_backend.backend.UserTickets;

@Repository
public interface UserTicketsRepository extends CrudRepository<UserTickets, Long> {
  Iterable<UserTickets> findByEmail(String email);
}