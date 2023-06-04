package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.cinema.controller.TicketController;
import ru.job4j.cinema.cinema.dto.FilmSessionsDto;
import ru.job4j.cinema.cinema.dto.FilmsDto;
import ru.job4j.cinema.cinema.model.Halls;
import ru.job4j.cinema.cinema.model.Ticket;
import ru.job4j.cinema.cinema.model.User;
import ru.job4j.cinema.cinema.service.FilmSessionsService;
import ru.job4j.cinema.cinema.service.FilmsService;
import ru.job4j.cinema.cinema.service.HallsService;
import ru.job4j.cinema.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketControllerTest {

    private TicketController ticketController;
    private FilmSessionsService filmSessionsService;
    private FilmsService filmsService;
    private HallsService hallsService;
    private TicketService ticketService;
    private HttpSession httpSession;

    @BeforeEach
    public void initServices() {
        filmSessionsService = mock(FilmSessionsService.class);
        filmsService = mock(FilmsService.class);
        hallsService = mock(HallsService.class);
        ticketService = mock(TicketService.class);
        httpSession =  mock(HttpSession.class);
        ticketController = new TicketController(ticketService, filmSessionsService, filmsService, hallsService);
    }

    @Test
    public void whenRequestFilmsSessionThenGetTimetable() {
        var idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        FilmSessionsDto filmSessions = new FilmSessionsDto(0, 1, "batman", 1,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.now().plusHours(2).truncatedTo(ChronoUnit.MINUTES), 400);
        FilmsDto films = FilmsDto.builder().id(1)
                .name("tirist")
                .description("an action movie about a spy")
                .year(2012)
                .genre("1")
                .minimalAge(21)
                .durationInMinutes(180)
                .file(1).build();
        Halls halls = new Halls(1, "Hall 1", 10, 50, "Hall 3d");

        when(filmSessionsService.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(filmSessions));
        when(filmsService.findById(films.getId())).thenReturn(Optional.of(films));
        when(hallsService.findById(halls.getId())).thenReturn(Optional.of(halls));

        var model = new ConcurrentModel();
        var view = ticketController.buyTicket(model, idArgumentCaptor.capture());
        var actualFilmSession = model.getAttribute("filmSessions");
        var actualFilms = model.getAttribute("film");
        var actualHalls = model.getAttribute("halls");

        assertThat(view).isEqualTo("tickets/buyTickets");
        assertThat(actualFilmSession).isEqualTo(filmSessions);
        assertThat(actualFilms).isEqualTo(films);
        assertThat(actualHalls).isEqualTo(halls);
    }

    @Test
    public void whenRequestFilmsSessionThenGetSuccess() {
        User user = new User(1, "name", "email", "1111");
        Ticket ticket = new Ticket(1, 1, 1, user.getId());
        var ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        var rowCountArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        var placeCountArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(httpSession.getAttribute("user")).thenReturn(user);
        when(ticketService.findByRAndP(rowCountArgumentCaptor.capture(), placeCountArgumentCaptor.capture()))
                .thenReturn(Optional.empty());
        when(ticketService.buyTicket(ticketArgumentCaptor.capture())).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var view = ticketController.buyTicket(model, ticket, 1, 1, httpSession);
        var actualTicket = model.getAttribute("ticket");

        assertThat(view).isEqualTo("tickets/success");
        assertThat(actualTicket).isEqualTo(ticket);
    }
}
