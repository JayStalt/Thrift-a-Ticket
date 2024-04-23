package com.example.java_backend.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BackendControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserConcertsService userConcertsService;

    @MockBean
    private UserTicketsService userTicketsService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddNewUser() throws Exception {
        String email = "test@example.com";
        String password = "password";
        User newUser = new User(email, password);

        // Mocking the saveUser method of the userService to return the newUser object
        when(userService.saveUser(newUser)).thenReturn(newUser);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/addUser")
                .contentType(MediaType.APPLICATION_JSON)  // Changed content type to JSON
                .content("{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}")  // Passing JSON content
                .accept(MediaType.APPLICATION_JSON))  // Accept JSON response
                .andReturn();

        assertEquals("Saved", result.getResponse().getContentAsString());
        verify(userService).saveUser(newUser);
    }

    @Test
    public void testAddNewUserConcert() throws Exception {
        UserConcerts userConcerts = new UserConcerts("test@example.com", "Artist", "Venue",
                "Date", "Time", "CityState");
        
        when(userConcertsService.saveUserConcert(userConcerts)).thenReturn(userConcerts);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/addUserConcert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userConcerts)))
                .andReturn();

        assertEquals("Saved", result.getResponse().getContentAsString());
        verify(userConcertsService).saveUserConcert(userConcerts);
    }

    @Test
    public void testAddNewUserTicket() throws Exception {
        UserTickets userTickets = new UserTickets("test@example.com", "Artist", "Venue",
                "Date", "Time", "CityState", "SeatNumber", "Price");
        
        when(userTicketsService.saveUserConcert(userTickets)).thenReturn(userTickets);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/addUserTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userTickets)))
                .andReturn();

        assertEquals("Saved", result.getResponse().getContentAsString());
        verify(userTicketsService).saveUserConcert(userTickets);
    }

    // Add more tests as needed for other controller methods

}
