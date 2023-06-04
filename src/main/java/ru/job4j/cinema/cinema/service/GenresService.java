package ru.job4j.cinema.cinema.service;

import ru.job4j.cinema.cinema.model.Genres;

import java.util.Optional;

/**
 * Интерфейс сервис - жанр
 * @author Buslaev
 */
public interface GenresService {
    Optional<Genres> findById(int id);
}
