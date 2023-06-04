package ru.job4j.cinema.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.cinema.model.Ticket;
import ru.job4j.cinema.cinema.repository.TicketRepository;

import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository sql2oTicketRepository;

    public SimpleTicketService(TicketRepository sql2oTicketRepository) {
        this.sql2oTicketRepository = sql2oTicketRepository;
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return sql2oTicketRepository.findById(id);
    }

    @Override
    public Optional<Ticket> buyTicket(Ticket ticket) {
        return sql2oTicketRepository.addTicket(ticket);
    }

    @Override
    public Optional<Ticket> findByRAndP(int row, int place) {
        return sql2oTicketRepository.findByRAndP(row, place);
    }
}
