package com.train.ticket.booking.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketRequest {
	
    private String from;
    private String to;
    private String user;
    private String section;

}