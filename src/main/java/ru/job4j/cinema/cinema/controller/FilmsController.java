package ru.job4j.cinema.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.cinema.service.FilmsService;

/**
 * Контроллер. Фильмы
 * @author Buslaev
 */
@Controller
@RequestMapping("/films")
public class FilmsController {

    private final FilmsService filmsService;

    public FilmsController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    /**
     * Возвращает предствление Кинотека.
     * Выводит список фильмов, которые показываются в кинотеатре
     * @param model
     * @return возвращает предствление Кинотека
     */
    @GetMapping({"/filmLibrary"})
    public String getFilms(Model model) {
        model.addAttribute("films", filmsService.findAll());
        return "filmLibrary";
    }
}
