package ru.job4j.cinema.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.cinema.model.User;
import ru.job4j.cinema.cinema.model.Ticket;
import ru.job4j.cinema.cinema.service.FilmSessionsService;
import ru.job4j.cinema.cinema.service.FilmsService;
import ru.job4j.cinema.cinema.service.HallsService;
import ru.job4j.cinema.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final FilmSessionsService filmSessionsService;
    private final FilmsService filmsService;
    private final HallsService hallsService;

    public TicketController(TicketService ticketService, FilmSessionsService filmSessionsService,
                            FilmsService filmsService, HallsService hallsService) {
        this.ticketService = ticketService;
        this.filmSessionsService = filmSessionsService;
        this.filmsService = filmsService;
        this.hallsService = hallsService;
    }

    @GetMapping("/buy/{idFS}")
    public String buyTicket(Model model, @PathVariable("idFS") int idFS) {
        var filmSessionsOptional = filmSessionsService.findById(idFS);
        if (filmSessionsOptional.isEmpty()) {
            model.addAttribute("message", "Ошибка сервиса");
            return "errors/404";
        }
        model.addAttribute("filmSessions", filmSessionsOptional.get());
        var filmsOptional = filmsService.findById(filmSessionsOptional.get().getFilmId());
        if (filmsOptional.isEmpty()) {
            model.addAttribute("message", "Ошибка сервиса");
            return "errors/404";
        }
        model.addAttribute("film", filmsOptional.get());
        var hallsOptional = hallsService.findById(filmSessionsOptional.get().getHallsId());
        if (hallsOptional.isEmpty()) {
            model.addAttribute("message", "Ошибка сервиса");
            return "errors/404";
        }
        model.addAttribute("halls", hallsOptional.get());
        return "tickets/buyTickets";
    }

    @PostMapping("/ticketBuy")
    public String buyTicket(Model model,
                            @ModelAttribute Ticket ticket,
                            @RequestParam("rowCount") int rowCount,
                            @RequestParam("placeCount") int placeCount,
                            HttpSession session) {
        ticket.setRowNumber(rowCount);
        ticket.setPlaceNumber(placeCount);
        User userMovie = (User) session.getAttribute("user");
        ticket.setUserId(userMovie.getId());
        var findTicket = ticketService.findByRAndP(ticket.getRowNumber(), ticket.getPlaceNumber());
        if (findTicket.isPresent()) {
            model.addAttribute("message", "Не удалось приобрести билет на заданное место.Вероятно оно уже занято. "
                    + "Перейдите на страницу бронирования билетов и попробуйте снова.");
            return "tickets/unsuccessful";
        }
        var buyTicket = ticketService.buyTicket(ticket);
        if (buyTicket.isEmpty()) {
            model.addAttribute("message", "Ошибка сервиса");
            return "errors/404";
        }
        model.addAttribute("ticket", buyTicket.get());
        return "tickets/success";
    }
}
