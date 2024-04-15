package com.train.ticket.booking.system.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.train.ticket.booking.system.dao.TicketRepository;
import com.train.ticket.booking.system.entity.Ticket;
import com.train.ticket.booking.system.model.TicketRequest;
import com.train.ticket.booking.system.service.TrainTicketService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TrainTicketControllerTest {
	
    @Mock
    private TrainTicketService ticketService;

    @InjectMocks
    private TrainTicketController ticketController;
    
    @Mock
    private TicketRepository ticketRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void purchaseTicket() throws Exception {
        TicketRequest ticketRequest = new TicketRequest("Chennai", "Bengaluru", "karthik.j@test.com", "1");
        mockMvc.perform(MockMvcRequestBuilders.post("/tickets/purchase")
                .content(objectMapper.writeValueAsString(ticketRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void getReceipt() throws Exception {
        String userId = "karthik.j@test.com";
        Ticket ticket = new Ticket("Chennai", "Bengaluru", "karthik.j@test.com", 5, "1", 14);
        when(ticketRepository.findByUser("karthik.j@test.com")).thenReturn(ticket);
        when(ticketService.getReceipt(userId)).thenReturn(ticket.toString());
        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/receipt/{userId}", userId))
                .andExpect(status().isOk());
    }
    
    @Test
    void getUsersBySection() throws Exception {
        String section = "1";
        List<String> userBySection = new ArrayList<String>();
        userBySection.add("John Doe + 2");
        when(ticketService.findUsersBySection(section)).thenReturn(userBySection);
        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/users/{section}", section))
                .andExpect(status().isOk());
    }

    @Test
    void modifySeat() {
        String userId = "karthik.j@test.com";
        String newSection = "B";
        when(ticketService.modifySeat(userId, newSection)).thenReturn(true);

        ResponseEntity<String> response = ticketController.modifySeat(userId, newSection);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User's seat modified successfully.", response.getBody());
        verify(ticketService, times(1)).modifySeat(userId, newSection);
    }
    
    @Test
    void removeUser() {
        String userId = "karthik.j@test.com";
        when(ticketService.removeUser(userId)).thenReturn(true);

        ResponseEntity<String> response = ticketController.removeUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User removed successfully.", response.getBody());
        verify(ticketService, times(1)).removeUser(userId);
    }
}
