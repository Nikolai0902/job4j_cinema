package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.cinema.model.Ticket;
import ru.job4j.cinema.cinema.model.User;
import ru.job4j.cinema.cinema.repository.Sql2oFilmSessionsRepository;
import ru.job4j.cinema.cinema.repository.Sql2oTicketRepository;
import ru.job4j.cinema.cinema.repository.Sql2oUserRepository;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;

import java.util.Properties;

public class Sql2oTicketRepositoryTest {

    private static Sql2oTicketRepository sql2oTicketRepository;
    private static Sql2oFilmSessionsRepository sql2oFilmSessionsRepository;
    private static Sql2oUserRepository sql2oUserRepository;

    private static User user1;
    private static User user2;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionsRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
        sql2oFilmSessionsRepository = new Sql2oFilmSessionsRepository(sql2o);
        sql2oUserRepository = new Sql2oUserRepository(sql2o);

        user1 = new User(1, "Nik", "2323@mail", "5151");
        user2 = new User(2, "Tom", "2656@mail", "5522");
        sql2oUserRepository.save(user1);
        sql2oUserRepository.save(user2);
    }

    @AfterAll
    public static void deleteFile() {
        sql2oUserRepository.deleteById(user1.getId());
        sql2oUserRepository.deleteById(user2.getId());
    }

    @AfterEach
    public void clearTickets() {
        var tickets = sql2oTicketRepository.findAll();
        for (var ticket : tickets) {
            sql2oTicketRepository.deleteById(ticket.getSessionId(), ticket.getRowNumber(), ticket.getPlaceNumber());
        }
    }

    @Test
    public void whenSaveTicketThenGetTicket() {
        var idSession = sql2oFilmSessionsRepository.findById(2).get().getId();
        Ticket ticket = new Ticket(idSession, 1, 2, user1.getId());
        var ticketOptional = sql2oTicketRepository
                .addTicket(ticket);
        assertThat(ticketOptional.get()).usingRecursiveComparison().isEqualTo(ticket);
    }

    @Test
    public void whenSaveTicketThenGetTicketRowPlace() {
        var idSession = sql2oFilmSessionsRepository.findById(2).get().getId();
        Ticket ticket = new Ticket(idSession, 7, 7, user2.getId());
        sql2oTicketRepository.addTicket(ticket);
        var savedTicket = sql2oTicketRepository
                .findByRAndP(ticket.getRowNumber(), ticket.getPlaceNumber());
        assertThat(savedTicket.get()).usingRecursiveComparison().isEqualTo(ticket);
    }

    @Test
    public void whenSaveTicketThenGetTicketFBI() {
        var idSession = sql2oFilmSessionsRepository.findById(2).get().getId();
        Ticket ticket = new Ticket(idSession, 7, 7, user1.getId());
        var id = sql2oTicketRepository.addTicket(ticket).get().getId();
        var ticketOptional = sql2oTicketRepository.findById(id);
        assertThat(ticketOptional.get()).usingRecursiveComparison().isEqualTo(ticket);
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oTicketRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oTicketRepository.findById(0)).isEqualTo(empty());
    }
}
