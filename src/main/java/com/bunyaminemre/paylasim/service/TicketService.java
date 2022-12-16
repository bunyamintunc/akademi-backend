package com.bunyaminemre.paylasim.service;

import com.bunyaminemre.paylasim.entitiy.Ticket;
import com.bunyaminemre.paylasim.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;


    public Ticket getTicketById(Long ticketId){
        return ticketRepository.findById(ticketId).orElseThrow(()-> new RuntimeException("ticket does not exist"));
    }

    public List<Ticket> getAllTicket(){
        return ticketRepository.findAll();
    }

    public Ticket createTicket(Ticket ticket){
      return ticketRepository.save(ticket);
    }

    public void deleteTicketById(Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()-> new RuntimeException("Ticket does not exist"));
        ticketRepository.delete(ticket);
    }

}
