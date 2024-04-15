package com.train.ticket.booking.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.train.ticket.booking.system.model.TicketRequest;
import com.train.ticket.booking.system.service.TrainTicketService;

@RestController
@RequestMapping("/tickets")
public class TrainTicketController {
	
	@Autowired
	private TrainTicketService ticketService;

	@PostMapping("/purchase")
	public ResponseEntity<String> purchaseTicket(@RequestBody TicketRequest ticketRequest) {
		ticketService.purchaseTicket(ticketRequest);
		return new ResponseEntity<>("Ticket purchased successfully.", HttpStatus.CREATED);
	}

	@GetMapping("/receipt/{userId}")
	public ResponseEntity<String> getReceipt(@PathVariable String userId) {
		String receipt = ticketService.getReceipt(userId);
		if (receipt != null) {
			return ResponseEntity.ok(receipt);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/users/{section}")
	public ResponseEntity<List<String>> getUsersBySection(@PathVariable String section) {
		List<String> usersSeatBySection = ticketService.findUsersBySection(section);
		if (usersSeatBySection != null) {
			return ResponseEntity.ok(usersSeatBySection);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/remove/{userId}")
	public ResponseEntity<String> removeUser(@PathVariable String userId) {
		if (ticketService.removeUser(userId)) {
			return ResponseEntity.ok("User removed successfully.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/modify/{userId}")
	public ResponseEntity<String> modifySeat(@PathVariable String userId, @RequestParam String newSection) {
		if (ticketService.modifySeat(userId, newSection)) {
			return ResponseEntity.ok("User's seat modified successfully.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}