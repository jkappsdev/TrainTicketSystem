package com.train.ticket.booking.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AutoConfiguration
public class TrainTicketBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainTicketBookingApplication.class, args);
	}
	
}
