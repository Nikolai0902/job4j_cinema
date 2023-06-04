package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.cinema.controller.FilmSessionController;
import ru.job4j.cinema.cinema.dto.FilmSessionsDto;
import ru.job4j.cinema.cinema.service.FilmSessionsService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilmSessionControllerTest {

    private FilmSessionController filmSessionController;
    private FilmSessionsService filmSessionsService;

    @BeforeEach
    public void initServices() {
        filmSessionsService = mock(FilmSessionsService.class);
        filmSessionController = new FilmSessionController(filmSessionsService);
    }

    @Test
    public void whenRequestFilmsSessionThenGetTimetable() {
        FilmSessionsDto filmSessions1 = new FilmSessionsDto(0, 1, "batman", 1,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.now().plusHours(2).truncatedTo(ChronoUnit.MINUTES), 400);
        FilmSessionsDto filmSessions2 = new FilmSessionsDto(1, 2, "batman2", 2,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.now().plusHours(3).truncatedTo(ChronoUnit.MINUTES), 600);

        Collection<FilmSessionsDto> expectedFilmsSession = List.of(filmSessions1, filmSessions2);
        when(filmSessionsService.findAll()).thenReturn(expectedFilmsSession);

        var model = new ConcurrentModel();
        var view = filmSessionController.getTimeTable(model);
        var actualFilmSession = model.getAttribute("filmSessions");

        assertThat(view).isEqualTo("timetable");
        assertThat(actualFilmSession).isEqualTo(expectedFilmsSession);
    }
}
