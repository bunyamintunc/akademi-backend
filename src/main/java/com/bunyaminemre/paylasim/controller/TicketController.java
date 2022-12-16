package com.bunyaminemre.paylasim.controller;

import com.bunyaminemre.paylasim.entitiy.Ticket;
import com.bunyaminemre.paylasim.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {


    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public Ticket createATicket(@RequestBody Ticket ticket){
        return ticketService.createTicket(ticket);
    }

    @GetMapping("/getall")
    public List<Ticket> getAllTicket(){
        return ticketService.getAllTicket();
    }

    @GetMapping("/getbyid/{ticketId}")
    public Ticket getTicketById(@PathVariable("ticketId") Long ticketid){
              return ticketService.getTicketById(ticketid);
    }

    @DeleteMapping("/deletebyid/{ticketId}")
    public void deleteTicketById(@PathVariable("ticketId") Long ticketId){
        ticketService.deleteTicketById(ticketId);
    }

}
