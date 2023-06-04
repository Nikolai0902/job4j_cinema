package ru.job4j.cinema.cinema.repository;

import ru.job4j.cinema.cinema.model.Genres;

import java.util.Optional;

/**
 * Интерфейс репозитория - жанра.
 * @author Buslaev
 */
public interface GenresRepository {
    Optional<Genres> findById(int id);
}
