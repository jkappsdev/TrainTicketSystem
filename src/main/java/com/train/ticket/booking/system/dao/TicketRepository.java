package com.train.ticket.booking.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.train.ticket.booking.system.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	Ticket findByUser(String user);

	@Query("SELECT t FROM Ticket t WHERE t.section = :section")
    List<Ticket> findUserBySection(@Param("section") String section);

    int deleteByUser(String user);
    
    @Query("SELECT t.seat FROM Ticket t where t.section = :section")
    int[] findBookedSeat(String section);
    
}