package com.example.java_backend.backend;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.example.java_backend.backend.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Transactional
  public User saveUser(User user) {
    Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
    if(existingUser.isPresent())
      return null;
    try {
      return userRepository.save(user);
    } catch (Exception e) {
        // Print the error message or handle the exception as needed
        System.err.println("Error occurred while saving user: " + e.getMessage());
        // You can also throw a custom exception or return a specific response if required
        throw new RuntimeException("Error occurred while saving user", e);
    }
  }

  public Iterable<User> findAll() {
    return userRepository.findAll();
  }

  public boolean inDatabase(User user) {
    Optional<User> potentialUser = userRepository.findByEmail(user.getEmail());
    return potentialUser.isPresent();
  }

}
