package com.example.java_backend.backend;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.java_backend.backend.UserConcerts;

@Repository
public interface UserConcertsRepository extends CrudRepository<UserConcerts, Long>{
  
}
