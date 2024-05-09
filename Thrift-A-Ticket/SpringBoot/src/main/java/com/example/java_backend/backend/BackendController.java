package com.example.java_backend.backend;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_backend.backend.User;

@RestController // This means that this class is a Controller
public class BackendController {
  
  @Autowired
  private final UserService userService;

  @Autowired
  private final UserTicketsService userTicketsService;

  @Autowired
  private final ticketAPIMain api;

  @Autowired
  public BackendController(UserService userService, UserTicketsService userTicketsService, ticketAPIMain api) {
    this.userService = userService;
    this.userTicketsService = userTicketsService;
    this.api = api;
  }

  @PostMapping(path="/addUser")
  public @ResponseBody int addNewUser(@RequestBody User u) {
      String encryptedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
      User n = new User(u.getEmail(), encryptedPassword);
      try {
          User savedUser = userService.saveUser(n);
          if (savedUser == null) {
              // User already exists
              return 0;
          } else {
              // User saved successfully
              return 2;
          }
      } catch (Exception e) {
          // Error occurred while saving user
          System.err.println("Error occurred while adding user: " + e.getMessage());
          e.printStackTrace();
          return 1;
      }
  }

  @PostMapping(path="/login")
  public @ResponseBody boolean login (@RequestBody User u) {
    String encryptedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
    User n = new User(u.getEmail(), encryptedPassword);
    return userService.inDatabase(n);
  }

  @PostMapping(path="/addUserTicket") // Map ONLY POST Requests
  public @ResponseBody String addNewUserTicket(@RequestBody UserTickets request) {
      // @ResponseBody means the returned String is the response, not a view name
      // @RequestBody binds the JSON data to the UserTicketRequest object
      UserTickets n = new UserTickets(request.getEmail(), request.getArtist(), request.getVenue(),
              request.getDate(), request.getTime(), request.getCityState(), request.getSeatNumber(), request.getPrice(), request.getPurchase_link());
      userTicketsService.saveUserConcert(n);
      return "Saved";
  }
  
  @GetMapping(path="/searchTickets/{event}/{state_letters}")
  public @ResponseBody UserTickets apiSearch(@PathVariable String event, @PathVariable String state_letters) {
    // This returns a JSON or XML with the users
    return api.callAPI(event, state_letters);
    //return new UserTickets();
  }

  @GetMapping(path="/searchTickets")
  public @ResponseBody Iterable<User> searchTickets() {
    // This returns a JSON or XML with the users
    return userService.findAll();
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    // This returns a JSON or XML with the users
    return userService.findAll();
  }

  @GetMapping(path="/allUserTickets")
  public @ResponseBody Iterable<UserTickets> getAllUserTickets() {
    // This returns a JSON or XML with the user tickets
    return userTicketsService.findAll();
  }

  @GetMapping("/userTickets/{email}")
    public @ResponseBody Iterable<UserTickets> getUserTickets(@PathVariable String email) {
    // This returns a JSON or XML with the user concerts
    return userTicketsService.findAllByEmail(email);
  }

}

