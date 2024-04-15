package com.train.ticket.booking.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Ticket {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "departure")
    private String from;
    
    @Column(name = "destination")
    private String to;
    
    @Column(name = "passenger")
    private String user;
    
    private double price;
    
    private String section;
    
    private int seat;

    public Ticket() {}

    public Ticket(String from, String to, String user, double price, String section, int seat) {
        this.from = from;
        this.to = to;
        this.user = user;
        this.price = price;
        this.section = section;
        this.seat = seat;
    }
    
}
