package com.train.ticket.booking.system.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.train.ticket.booking.system.dao.TicketRepository;
import com.train.ticket.booking.system.entity.Ticket;
import com.train.ticket.booking.system.model.TicketRequest;

import jakarta.transaction.Transactional;

@Service
public class TrainTicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public void purchaseTicket(TicketRequest ticketRequest) {
    	int seatNumber = getAvailableSeat(ticketRequest.getSection());
        Ticket ticket = new Ticket(ticketRequest.getFrom(), ticketRequest.getTo(), ticketRequest.getUser(), 5.0, ticketRequest.getSection(), seatNumber);
        ticketRepository.save(ticket);
    }

    public String getReceipt(String userId) {
        Ticket ticket = ticketRepository.findByUser(userId);
        if (ticket != null) {
            return ticket.toString();
        } else {
            return null;
        }
    }

    public List<String> findUsersBySection(String section) {
        return getUserBySection(ticketRepository.findUserBySection(section));
    }
    
    private List<String> getUserBySection(List<Ticket> tickets) {
		List<String> usersSeatBySection = new ArrayList<String>();
		for (Ticket t: tickets) {
			usersSeatBySection.add(" User: " + t.getUser() + " Seat Number: "+ t.getSeat());
		}
		return usersSeatBySection;
	}

    @Transactional
    public boolean removeUser(String userId) {
        return ticketRepository.deleteByUser(userId) > 0;
    }

    public boolean modifySeat(String userId, String newSection) {
        Ticket ticket = ticketRepository.findByUser(userId);
        int seatInNewSection = getAvailableSeat(newSection);
        if (ticket != null && seatInNewSection > 0) {
        	ticket.setSeat(seatInNewSection);
            ticket.setSection(newSection);
            ticketRepository.save(ticket);
            return true;
        } else {
            return false;
        }
    }
    
    public int getAvailableSeat(String section) {
		int[] bookedSeats =  ticketRepository.findBookedSeat(section);
    	List<Integer> list = Arrays.stream(bookedSeats).boxed().collect(Collectors.toList());
    	int seatNumber = 0;
    	if (bookedSeats.length < 200) {
    		boolean isSeatAvailable = true;
    		int i = 0;
    		while (isSeatAvailable && i<=200) {
    			int randomSeat = new Random().nextInt(1, 200);
    			if(!list.contains(randomSeat)) {
    				seatNumber = randomSeat;
        			isSeatAvailable = false;
        		}
    			i++;
    		}
    	}
		return seatNumber;
	}
}
