package com.example.java_backend.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.example.java_backend.backend.UserTicketsRepository;

@Service
public class UserTicketsService {
    
  @Autowired
  private UserTicketsRepository userTicketsRepository;

  @Transactional
  public UserTickets saveUserConcert(UserTickets userTickets) {
    try {
      return userTicketsRepository.save(userTickets);
    } catch (Exception e) {
        // Print the error message or handle the exception as needed
        System.err.println("Error occurred while saving user: " + e.getMessage());
        // You can also throw a custom exception or return a specific response if required
        throw new RuntimeException("Error occurred while saving user", e);
    }
  }

  public Iterable<UserTickets> findAll() {
    return userTicketsRepository.findAll();
  }

  public Iterable<UserTickets> findAllByEmail(String email) {
    return userTicketsRepository.findByEmail(email);
  }
}
