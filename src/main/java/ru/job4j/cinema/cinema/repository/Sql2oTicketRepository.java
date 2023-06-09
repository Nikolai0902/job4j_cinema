package ru.job4j.cinema.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.cinema.model.Ticket;
import ru.job4j.cinema.cinema.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Реализация репозитория - билеты.
 * @author Buslaev
 */
@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private final Sql2o sql2o;

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oTicketRepository.class.getName());

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Поиск билета в БД по id.
     * @param id id
     * @return билет обернутый в Optional.
     */
    @Override
    public Optional<Ticket> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets WHERE id = :id");
            query.addParameter("id", id);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    /**
     * Вставка билета в БД.
     * @param ticket билет.
     * @return билет обернутый в Optional.
     */
    @Override
    public Optional<Ticket> addTicket(Ticket ticket) {
        Optional<Ticket> ticketOptional = Optional.empty();
        try (var connection = sql2o.open()) {
            var sql = """
                      INSERT INTO tickets(session_id, row_number, place_number, user_id)
                      VALUES (:sessionId, :rowNumber, :placeNumber, :userId)
                      """;
            var query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            ticketOptional = Optional.of(ticket);
        } catch (Exception e) {
            LOG.error("Exception connection", e);
        }
        return ticketOptional;
    }

    /**
     * Поиск билета.
     * @param row ряд
     * @param place место
     * @return билет обернутый в Optional.
     */
    @Override
    public Optional<Ticket> findByRAndP(int row, int place) {
        Optional<Ticket> rsl = Optional.empty();
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets WHERE row_number = :rowNumber AND place_number = :placeNumber");
            query.addParameter("rowNumber", row);
            query.addParameter("placeNumber", place);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            rsl = Optional.ofNullable(ticket);
        } catch (Exception e) {
            LOG.error("Exception connection", e);
        }
        return rsl;
    }

    /**
     * Все билеты.
     * @return коллекция билетов.
     */
    @Override
    public Collection<Ticket> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets");
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }

    /**
     * Удаление билета из БД.
     * @param sessionId id сессии.
     * @param rowNumber ряд
     * @param placeNumber место
     */
    @Override
    public boolean deleteById(int sessionId, int rowNumber, int placeNumber) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM tickets WHERE session_id = :sessionId AND row_number = :rowNumber AND place_number = :placeNumber");
            query.addParameter("sessionId", sessionId);
            query.addParameter("rowNumber", rowNumber);
            query.addParameter("placeNumber", placeNumber);
            var result = query.executeUpdate().getResult();
            return result > 0;
        }
    }
}
