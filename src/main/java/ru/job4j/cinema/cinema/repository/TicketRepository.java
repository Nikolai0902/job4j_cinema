package ru.job4j.cinema.cinema.repository;

import ru.job4j.cinema.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс репозитория - билета.
 * @author Buslaev
 */
public interface TicketRepository {

    Optional<Ticket> findById(int id);

    Optional<Ticket> addTicket(Ticket ticket);

    Optional<Ticket> findByRAndP(int row, int place);

    Collection<Ticket> findAll();

    boolean deleteById(int sessionId, int rowNumber, int placeNumber);
}
