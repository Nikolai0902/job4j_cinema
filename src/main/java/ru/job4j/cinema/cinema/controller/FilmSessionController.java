package ru.job4j.cinema.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.cinema.service.FilmSessionsService;

/**
 * Контроллер. Расписание.
 * @author Buslaev
 */
@Controller
@RequestMapping("/filmSessions")
public class FilmSessionController {

    private final FilmSessionsService filmSessionsService;

    public FilmSessionController(FilmSessionsService filmSessionsService) {
        this.filmSessionsService = filmSessionsService;
    }

    /**
     * Возвращает предствление расписание.
     * Выводит сеансы и связанные с ними фильмы.
     * @param model
     * @return возвращает предствление расписание.
     */
    @GetMapping("/timetable")
    public String getTimeTable(Model model) {
        model.addAttribute("filmSessions", filmSessionsService.findAll());
        return "timetable";
    }
}
