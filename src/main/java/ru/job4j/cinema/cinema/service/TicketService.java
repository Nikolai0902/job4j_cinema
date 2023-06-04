package ru.job4j.cinema.cinema.service;

import ru.job4j.cinema.cinema.model.Ticket;

import java.util.Optional;

public interface TicketService {

    Optional<Ticket> findById(int id);

    Optional<Ticket> buyTicket(Ticket ticket);

    Optional<Ticket> findByRAndP(int row, int place);
}
