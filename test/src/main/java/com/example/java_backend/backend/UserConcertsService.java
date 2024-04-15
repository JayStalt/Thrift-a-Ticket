package com.example.java_backend.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.example.java_backend.backend.UserConcertsRepository;

@Service
public class UserConcertsService {
  
  @Autowired
  private UserConcertsRepository userConcertsRepository;

  @Transactional
  public UserConcerts saveUserConcert(UserConcerts userConcerts) {
    try {
      return userConcertsRepository.save(userConcerts);
    } catch (Exception e) {
        // Print the error message or handle the exception as needed
        System.err.println("Error occurred while saving user: " + e.getMessage());
        // You can also throw a custom exception or return a specific response if required
        throw new RuntimeException("Error occurred while saving user", e);
    }
  }

  public Iterable<UserConcerts> findAll() {
    return userConcertsRepository.findAll();
  }

}
