package ru.job4j.cinema.cinema.service;

import ru.job4j.cinema.cinema.model.Halls;

import java.util.Optional;

/**
 * Интерфейс сервис - зал
 * @author Buslaev
 */
public interface HallsService {
    Optional<Halls> findById(int id);
}
