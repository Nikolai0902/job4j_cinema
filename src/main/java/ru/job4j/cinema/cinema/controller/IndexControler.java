package ru.job4j.cinema.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.cinema.service.FilmsService;

/**
 * Контроллер. Главная страница.
 * @author Buslaev
 */
@Controller
public class IndexControler {

    private final FilmsService filmsService;

    public IndexControler(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    /**
     * Возвращает предствление главная страницае.
     * Выводит общую информацию о ресурсе.
     * @param model
     * @return возвращает предствление главная страницае.
     */
    @GetMapping({"/logo", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("films", filmsService.findAll());
        return "index";
    }
}
