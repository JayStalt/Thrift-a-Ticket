package com.example.java_backend.backend;

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
  private final UserConcertsService userConcertsService;

  @Autowired
  private final UserTicketsService userTicketsService;

  @Autowired
  public BackendController(UserService userService, UserConcertsService userConcertsService, UserTicketsService userTicketsService) {
    this.userService = userService;
    this.userConcertsService = userConcertsService;
    this.userTicketsService = userTicketsService;
  }

  @PostMapping(path="/addUser")
  public @ResponseBody String addNewUser (@RequestBody User u) {
    User n = new User(u.getEmail(), u.getPassword());
    userService.saveUser(n);
    return "Saved";
  }

  @PostMapping(path="/addUserConcert") // Map ONLY POST Requests
  public @ResponseBody String addNewUserConcert(@RequestBody UserConcerts request) {
      // @ResponseBody means the returned String is the response, not a view name
      // @RequestBody binds the JSON data to the UserConcertRequest object
      UserConcerts n = new UserConcerts(request.getEmail(), request.getArtist(), request.getVenue(),
              request.getDate(), request.getTime(), request.getCityState());
      userConcertsService.saveUserConcert(n);
      return "Saved";
  }
  
  @PostMapping(path="/addUserTicket") // Map ONLY POST Requests
  public @ResponseBody String addNewUserTicket(@RequestBody UserTickets request) {
      // @ResponseBody means the returned String is the response, not a view name
      // @RequestBody binds the JSON data to the UserTicketRequest object
      UserTickets n = new UserTickets(request.getEmail(), request.getArtist(), request.getVenue(),
              request.getDate(), request.getTime(), request.getCityState(), request.getSeatNumber(), request.getPrice());
      userTicketsService.saveUserConcert(n);
      return "Saved";
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

  @GetMapping(path="/allUserConcerts")
  public @ResponseBody Iterable<UserConcerts> getAllUserConcerts() {
    // This returns a JSON or XML with the user concerts
    return userConcertsService.findAll();
  }

  @GetMapping("/userTickets/{email}")
    public @ResponseBody Iterable<UserTickets> getUserTickets(@PathVariable String email) {
    // This returns a JSON or XML with the user concerts
    return userTicketsService.findAllByEmail(email);
  }
}

