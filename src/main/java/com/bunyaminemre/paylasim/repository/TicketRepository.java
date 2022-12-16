package com.bunyaminemre.paylasim.repository;

import com.bunyaminemre.paylasim.entitiy.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

}
