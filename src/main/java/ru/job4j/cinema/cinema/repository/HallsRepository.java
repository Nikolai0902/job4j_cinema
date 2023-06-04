package ru.job4j.cinema.cinema.repository;

import ru.job4j.cinema.cinema.model.Halls;

import java.util.Optional;

/**
 * Интерфейс репозитория - зала.
 * @author Buslaev
 */
public interface HallsRepository {
    Optional<Halls> findById(int id);
}
