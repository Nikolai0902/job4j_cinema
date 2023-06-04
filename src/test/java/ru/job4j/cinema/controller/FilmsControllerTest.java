package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.cinema.controller.FilmsController;
import ru.job4j.cinema.cinema.dto.FilmsDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import ru.job4j.cinema.cinema.service.FilmsService;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilmsControllerTest {

    private FilmsController filmsController;
    private FilmsService filmsService;

    @BeforeEach
    public void initServices() {
        filmsService = mock(FilmsService.class);
        filmsController = new FilmsController(filmsService);
    }

    @Test
    public void whenRequestFilmsListPageThenGetPageFilmLibrary() {
        FilmsDto films1 = FilmsDto.builder().id(1)
                .name("tirist")
                .description("an action movie about a spy")
                .year(2012)
                .genre("1")
                .minimalAge(21)
                .durationInMinutes(180)
                .file(1).build();
        FilmsDto films2 = FilmsDto.builder().id(2)
                .name("Rembo")
                .description("action movie")
                .year(2012)
                .genre("2")
                .minimalAge(21)
                .durationInMinutes(180)
                .file(2).build();
        Collection<FilmsDto> expectedFilms = List.of(films1, films2);
        when(filmsService.findAll()).thenReturn(expectedFilms);

        var model = new ConcurrentModel();
        var view = filmsController.getFilms(model);
        var actualFilms = model.getAttribute("films");

        assertThat(view).isEqualTo("filmLibrary");
        assertThat(actualFilms).isEqualTo(expectedFilms);
    }
}
